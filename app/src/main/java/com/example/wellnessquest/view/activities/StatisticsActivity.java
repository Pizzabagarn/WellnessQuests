package com.example.wellnessquest.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.wellnessquest.R;

public class StatisticsActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(
                R.layout.activity_statistics,
                drawerBinding.contentFrame,
                true
        );
    }
}