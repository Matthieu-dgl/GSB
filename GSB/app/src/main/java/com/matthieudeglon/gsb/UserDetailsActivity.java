package com.matthieudeglon.gsb;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textViewUsername = findViewById(R.id.textViewUsername);

        // Ici, vous récupéreriez les informations de l'utilisateur depuis l'API "GSB"
        // et les afficheriez sur cette activité
        displayUserInfo("John Doe"); // Exemple de nom d'utilisateur pour la démonstration
    }

    private void displayUserInfo(String username) {
        textViewUsername.setText(username);
        // Ici, vous afficheriez d'autres informations de l'utilisateur
    }
}