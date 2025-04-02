package com.example.wellnessquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //Refererar till ikonerna i activity_main.xml

            ImageView iconQuests = findViewById(R.id.imgQuestsIcon);
            ImageView iconMap = findViewById(R.id.imgMapIcon);
            ImageView iconProfile = findViewById(R.id.imgUserIcon);
            TextView txtCoins = findViewById(R.id.txtCoins);


            iconProfile.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                startActivity(intent);
            });


            iconQuests.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, QuestActivity.class);
                startActivity(intent);
            });



            iconMap.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            });
            return insets;

        });
    }
}