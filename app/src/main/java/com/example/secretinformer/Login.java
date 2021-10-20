package com.example.secretinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText userEmailL, userPasswordL;
    Button loginUser;
    BackGroundWorker backGroundWorker;
    //Bundle bundle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailL = (EditText) findViewById(R.id.userEmailL);
        userPasswordL = (EditText) findViewById(R.id.userPasswordL);

        loginUser = (Button) findViewById(R.id.loginUser);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for now
                //bundle = getIntent().getExtras();
                sharedPreferences = getSharedPreferences("RV", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("email", userEmailL.getText().toString());
                editor.apply();
                loginUser(userEmailL.getText().toString(), userPasswordL.getText().toString());
            }
        });
    }

    public void loginUser(String uName, String uPass) {
        backGroundWorker = new BackGroundWorker(this);
        backGroundWorker.execute("login", uName, uPass);
    }
}
