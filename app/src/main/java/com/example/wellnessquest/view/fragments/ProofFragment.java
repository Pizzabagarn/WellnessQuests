// ProofFragment.java
package com.example.wellnessquest.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.FragmentProofBinding;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.utils.QuestVerifier;
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.view.activities.MapActivity;
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
            binding.setQuest(quest);
        } else {
            Toast.makeText(getContext(), "No quest data", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.buttonSelectImage.setOnClickListener(v -> {
            SoundManager.getInstance(requireContext()).suppressAutoPause();
            galleryLauncher.launch("image/*");
        });

        binding.buttonTakePhoto.setOnClickListener(v -> {
            SoundManager.getInstance(requireContext()).suppressAutoPause();
            dispatchTakePictureIntent();
        });

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

                SoundManager.getInstance(requireContext()).playSuccess();

                // 🔢 Antal coins
                int coins = quest.getRewardCoins(); // t.ex. 10

                // 👇 Visa animation
                animateCoinReward(coins);

                binding.textResult.setText("Verified! Quest completed");
                userViewModel.completeQuest(quest, imageUri.toString(), description);

                // Visa coin-animation och text
                binding.coinAnimation.setVisibility(View.VISIBLE);
                binding.coinRewardText.setVisibility(View.VISIBLE);

                // Animera coin: typ studs eller fade
                binding.coinAnimation.setScaleX(0f);
                binding.coinAnimation.setScaleY(0f);
                binding.coinAnimation.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(500)
                        .start();

                // Fade in coin-text
                binding.coinRewardText.setAlpha(0f);
                binding.coinRewardText.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .start();

                // Gå tillbaka efter delay
                new android.os.Handler().postDelayed(() -> {

                    requireActivity().getSupportFragmentManager().popBackStack();
                }, 1500);
            }




            @Override
            public void onFailed() {
                SoundManager.getInstance(requireContext()).playError();
                binding.textResult.setText("The image doesn't match the quest. Try again!");
            }



            @Override
            public void onMismatch(List<ImageLabel> labels) {
                SoundManager.getInstance(requireContext()).playError();
                binding.textResult.setText("The image doesn't match the quest. Try again!\n" + labels.toString());
            }

            @Override
            public void onError(Exception e) {
                SoundManager.getInstance(requireContext()).playError();
                Toast.makeText(getContext(), "Verification error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void animateCoinReward(int rewardAmount) {
        binding.coinFlyIcon.setTranslationY(0f);
        binding.coinFlyText.setTranslationY(0f);
        binding.coinFlyIcon.setVisibility(View.VISIBLE);
        binding.coinFlyText.setVisibility(View.VISIBLE);

        binding.coinFlyIcon.setAlpha(0f);
        binding.coinFlyText.setAlpha(0f);
        binding.coinFlyIcon.animate().alpha(1f).setDuration(300).start();
        binding.coinFlyText.animate().alpha(1f).setDuration(300).start();

        final int[] current = {0};
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (current[0] <= rewardAmount) {
                    binding.coinFlyText.setText("+" + current[0]);
                    current[0]++;
                    handler.postDelayed(this, 50);
                }
            }
        }, 300);

        long flyDelay = 1000;
        float flyDistance = -600f;

        binding.coinFlyIcon.animate()
                .translationY(flyDistance)
                .alpha(0f)
                .setStartDelay(flyDelay)
                .setDuration(800)
                .withEndAction(() -> {
                    binding.coinFlyIcon.setVisibility(View.GONE);
                    binding.coinFlyIcon.setAlpha(1f);
                    binding.coinFlyIcon.setTranslationY(0f);
                })
                .start();

        binding.coinFlyText.animate()
                .translationY(flyDistance)
                .alpha(0f)
                .setStartDelay(flyDelay)
                .setDuration(800)
                .withEndAction(() -> {
                    binding.coinFlyText.setVisibility(View.GONE);
                    binding.coinFlyText.setAlpha(1f);
                    binding.coinFlyText.setTranslationY(0f);

                })
                .start();
    }


}
