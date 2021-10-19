package com.example.secretinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText userNameL, userPasswordL;
    Button loginUser;
    BackGroundWorker backGroundWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameL = (EditText) findViewById(R.id.userNameL);
        userPasswordL = (EditText) findViewById(R.id.userPasswordL);

        loginUser = (Button) findViewById(R.id.loginUser);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(userNameL.getText().toString(), userPasswordL.getText().toString());
            }
        });
    }

    public void loginUser(String uName, String uPass) {
        backGroundWorker = new BackGroundWorker(this);
        backGroundWorker.execute("login", uName, uPass);
    }
}
