package com.matthieudeglon.gsb;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in... Please wait.");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);
        login.setOnClickListener(v -> {
            String userVar = username.getText().toString();
            String passVar = password.getText().toString();
            if (userVar.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Username cannot be blank",
                        Toast.LENGTH_SHORT).show();
            } else if (passVar.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Password cannot be blank",
                        Toast.LENGTH_SHORT).show();
            } else {
                loginRequest(userVar, passVar);
            }
        });
    }
    private void loginRequest(String userVar, String passVar) {
        String loginUrl = "https://matthieudeglon.com/AppCR-GSB-main-2/Apis/ApiLogin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if (status == 200) { // Success
                            Toast.makeText(LoginActivity.this, "Login successful",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Incorrect username or password",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Failed to parse server response",
                                Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Failed to connect to server. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", userVar);
                params.put("password", passVar);
                return params;
            }
        };
        progressDialog.show();
        requestQueue.add(stringRequest);
    }
}