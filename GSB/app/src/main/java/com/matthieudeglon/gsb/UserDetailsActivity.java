package com.matthieudeglon.gsb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("user_email");
        String userName = intent.getStringExtra("user_name");
        String userSurname = intent.getStringExtra("user_surname");
        String userRegion = intent.getStringExtra("user_region");
        String userType = intent.getStringExtra("user_type");
        String userLastco = intent.getStringExtra("user_time");

        TextView emailTextView = findViewById(R.id.InfoEmail);
        TextView nameTextView = findViewById(R.id.InfoName);
        TextView surnameTextView = findViewById(R.id.InfoSurname);
        TextView regionTextView = findViewById(R.id.InfoRegion);
        TextView typeTextView = findViewById(R.id.InfoType);
        TextView timeTextView = findViewById(R.id.InfoLastCo);
        emailTextView.setText(userEmail);
        nameTextView.setText(userName);
        surnameTextView.setText(userSurname);
        regionTextView.setText(userRegion);
        typeTextView.setText(userType);
        timeTextView.setText(userLastco);
    }
}