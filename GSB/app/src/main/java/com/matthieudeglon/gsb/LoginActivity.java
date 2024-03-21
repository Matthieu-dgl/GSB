package com.matthieudeglon.gsb;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, mdpInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email);
        mdpInput = findViewById(R.id.mdp);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginMethod();
            }
        });
    }

    public void LoginMethod() {
        String email = emailInput.getText().toString();
        String password = mdpInput.getText().toString();

        new Login(LoginActivity.this).execute(email, password);
    }

    private static class Login extends AsyncTask<String, Void, String> {
        private AppCompatActivity mActivity;

        Login(AppCompatActivity activity) {
            mActivity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String result = "";

            try {
                Log.d("Login", "Connection à l'API...");

                URL url = new URL("https://matthieudeglon.com/AppCR-GSB-main-2/Apis/ApiLogin.php");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                conn.disconnect();

                Log.d("Login", "API Response: " + result);
            } catch (IOException e) {
                Log.e("Login", "Error: " + e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);

                if (jsonResponse.has("status")) {
                    int status = jsonResponse.getInt("status");
                    if (status == 200) {
                        JSONObject userObject = jsonResponse.getJSONObject("user");

                        String userName = userObject.getString("Nom");
                        String userFirstName = userObject.getString("Prenom");
                        String userEmail = userObject.getString("Email");
                        String userRegion = userObject.getString("Region");
                        String userType = userObject.getString("Type");
                        String userLastCo = userObject.getString("TimeStamp");

                        Intent intent = new Intent(mActivity, UserDetailsActivity.class);
                        intent.putExtra("user_email", userEmail);
                        intent.putExtra("user_name", userName);
                        intent.putExtra("user_surname", userFirstName);
                        intent.putExtra("user_region", userRegion);
                        intent.putExtra("user_type", userType);
                        intent.putExtra("user_time", userLastCo);


                        mActivity.startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "L'utilisateur n'éxiste pas", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(mActivity, "Le 'status' est manquant dans la réponse JSON", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mActivity, "Erreur lors dans la réponse JSON", Toast.LENGTH_SHORT).show();
            }
        }


    }


}