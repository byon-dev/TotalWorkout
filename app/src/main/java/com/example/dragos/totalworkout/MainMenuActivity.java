package com.example.dragos.totalworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class MainMenuActivity extends AppCompatActivity {
    ImageButton total_workout_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        final TextView etUser = (TextView) findViewById(R.id.tvUser);
        final ImageView ivFacebook = findViewById(R.id.ivFacebook);


        Intent intent = getIntent();

        String user = intent.getStringExtra("user");
        String userid = intent.getStringExtra("userid");
       /* ImageView profile = intent
*/


        etUser.setText(user);

        try {
            URL profile_picture =  new URL("https://graph.facebook.com/"+userid+"/picture?width=250&height=250");
            Picasso.get().load(profile_picture.toString()).into(ivFacebook);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


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
