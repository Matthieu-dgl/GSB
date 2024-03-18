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

    private TextView textViewUsername;
    private TextView textViewNom;
    private TextView textViewPrenom;
    private TextView textViewType;
    private TextView textViewRegion;
    private TextView textViewTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewNom = findViewById(R.id.textViewNom);
        textViewPrenom = findViewById(R.id.textViewPrenom);
        textViewType = findViewById(R.id.textViewType);
        textViewRegion = findViewById(R.id.textViewRegion);
        textViewTimestamp = findViewById(R.id.textViewTimestamp);

        String userDataJsonString = getIntent().getStringExtra("userDataJsonString");

        try {
            JSONObject userDataJson = new JSONObject(userDataJsonString);

            JSONObject user = userDataJson.getJSONObject("user");
            String username = user.getString("Email");
            String nom = user.getString("Nom");
            String prenom = user.getString("Prenom");
            String type = user.getString("Type");
            String region = user.getString("Region");
            String timestamp = user.getString("TimeStamp");

            textViewUsername.setText("Email: " + username);
            textViewNom.setText("Nom: " + nom);
            textViewPrenom.setText("Prénom: " + prenom);
            textViewType.setText("Type: " + type);
            textViewRegion.setText("Région: " + region);
            textViewTimestamp.setText("Timestamp: " + timestamp);

            Intent intent = new Intent(UserDetailsActivity.this, LoginActivity.class);
            intent.putExtra("userData", user.toString());
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}