package com.example.dragos.totalworkout;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    Button BTN_Continue;
    LoginButton loginButton;
    TextView loginStatus;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        loginStatus = (TextView) findViewById(R.id.loginStatus);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



                GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject user, GraphResponse response) {

                                Intent IntentLoadMainMenuActivity = new Intent(LoginActivity.this, MainMenuActivity.class);
                                IntentLoadMainMenuActivity.putExtra("user",user.optString("name"));
                                IntentLoadMainMenuActivity.putExtra("userid",user.optString("id"));
                                LoginActivity.this.startActivity(IntentLoadMainMenuActivity);


                            }
                        }).executeAsync();

            }

            @Override
            public void onCancel() {

                loginStatus.setText("Login Cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent( LoginActivity.this , RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String name = jsonResponse.getString("name");

                                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                                intent.putExtra("user",name);


                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
        BTN_Continue = (Button) findViewById( R.id.BTN_cancel_log_in);

        BTN_Continue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentLoadMainMenuActivity = new Intent(LoginActivity.this, MainMenuActivity.class);
                IntentLoadMainMenuActivity.putExtra("user","Anonymous");
                startActivity(IntentLoadMainMenuActivity);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            callbackManager.onActivityResult(requestCode, resultCode,data);
    }
}
