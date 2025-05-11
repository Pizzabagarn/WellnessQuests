// ProofFragment.java
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
import com.example.wellnessquest.utils.QuestVerifier;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.mlkit.vision.label.ImageLabel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProofFragment extends Fragment {

    private FragmentProofBinding binding;
    private Uri imageUri;
    private Uri photoUri;
    private String currentPhotoPath;
    private Quest quest;
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

        if (getArguments() != null && getArguments().containsKey("quest")) {
            quest = (Quest) getArguments().getSerializable("quest");
        } else {
            Toast.makeText(getContext(), "No quest data", Toast.LENGTH_SHORT).show();
            return;
        }

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
                    imageUri = photoUri;
                    binding.imagePreview.setImageURI(imageUri);
                }
            });

    private void dispatchTakePictureIntent() {
        Context context = requireContext();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
            photoUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraLauncher.launch(takePictureIntent);
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

        QuestVerifier.verify(requireContext(), imageUri, quest, description, new QuestVerifier.VerificationCallback() {
            @Override
            public void onVerified() {
                binding.textResult.setText("Verified! Quest completed");
                userViewModel.completeQuest(quest, imageUri.toString(), description);
            }

            @Override
            public void onFailed() {
                binding.textResult.setText("The image doesn't match the quest. Try again!");
            }

            @Override
            public void onMismatch(List<ImageLabel> labels) {
                binding.textResult.setText("The image doesn't match the quest. Try again!\n" + labels.toString());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Verification error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
