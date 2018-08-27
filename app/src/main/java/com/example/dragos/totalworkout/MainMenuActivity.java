package com.example.dragos.totalworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    ImageButton total_workout_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);

        total_workout_button=(ImageButton) findViewById(R.id.BTN_wk_total_body);

        total_workout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intendloadnewactivity= new Intent (MainMenuActivity.this,WorkoutTotalBody.class);
                startActivity(Intendloadnewactivity);
            }
        });
    }
}
