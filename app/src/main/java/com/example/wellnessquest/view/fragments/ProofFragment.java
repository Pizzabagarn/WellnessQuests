package com.example.wellnessquest.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.databinding.FragmentProofBinding;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProofFragment extends Fragment {

    private FragmentProofBinding binding;
    private Uri imageUri;
    private String currentPhotoPath;
    private Quest testQuest;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProofBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        testQuest = new Quest("q1", "Gå en promenad", "Promenera i minst 10 minuter", "Fitness", false, 10);

        binding.buttonSelectImage.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        binding.buttonTakePhoto.setOnClickListener(v -> dispatchTakePictureIntent());
        binding.buttonVerify.setOnClickListener(v -> verifyImage());
    }

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageUri = uri;
                    binding.imagePreview.setImageURI(uri);
                }
            });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    File f = new File(currentPhotoPath);
                    imageUri = Uri.fromFile(f);
                    binding.imagePreview.setImageURI(imageUri);
                }
            });

    private void dispatchTakePictureIntent() {
        Context context = requireContext();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void verifyImage() {
        if (imageUri == null) {
            Toast.makeText(getContext(), "Choose existing or take new image", Toast.LENGTH_SHORT).show();
            return;
        }

        String description = binding.editDescription.getText().toString().toLowerCase();
        if (description.isEmpty()) {
            Toast.makeText(getContext(), "Write a description of what you did", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            InputImage image = InputImage.fromFilePath(requireContext(), imageUri);
            ImageLabeler labeler = ImageLabeling.getClient(new ImageLabelerOptions.Builder().build());
            labeler.process(image)
                    .addOnSuccessListener(this::handleLabels)
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Analyze failed", Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLabels(List<ImageLabel> labels) {
        boolean matchFound = false;
        for (ImageLabel label : labels) {
            String tag = label.getText().toLowerCase();
            float confidence = label.getConfidence();
            if (confidence > 0.7f) {
                if (testQuest.getCategory().equalsIgnoreCase("fitness") && (tag.contains("outdoor") || tag.contains("person") || tag.contains("walk"))) {
                    matchFound = true;
                }
                if (testQuest.getCategory().equalsIgnoreCase("mind") && (tag.contains("book") || tag.contains("journal") || tag.contains("writing"))) {
                    matchFound = true;
                }
            }
        }

        if (matchFound) {
            binding.textResult.setText("Verified! Quest completed");
            userViewModel.completeQuest(testQuest, imageUri.toString(), binding.editDescription.getText().toString());
        } else {
            binding.textResult.setText("The image doesn't match the quest. Try again!");
        }
    }
}
