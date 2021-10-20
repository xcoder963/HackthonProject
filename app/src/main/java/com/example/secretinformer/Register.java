package com.example.secretinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText userName, userPassword, userEmail, userPHNO;
    Button registerUser;
    BackGroundWorker backGroundWorker;
    TextView loadLogin;
    InternalDatabase internalDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        internalDatabase = new InternalDatabase(this);

        try {
            Cursor rs = internalDatabase.getData();
            rs.moveToFirst();
            if (!rs.getString(rs.getColumnIndex("name")).isEmpty()) {
                Intent i = new Intent(getBaseContext(), Login.class);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "This activity will not occur after registeration is done", Toast.LENGTH_LONG).show();
        }

        userName = (EditText) findViewById(R.id.userName);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPHNO = (EditText) findViewById(R.id.userPHNO);
        userPassword = (EditText) findViewById(R.id.userPassword);

        registerUser = (Button) findViewById(R.id.registerUser);

        loadLogin = (TextView) findViewById(R.id.loadLogin);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(userName.getText().toString(), userEmail.getText().toString(), userPHNO.getText().toString(), userPassword.getText().toString());
            }
        });

        loadLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getBaseContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void registerUser(String uName, String email, String phno, String uPass) {
        backGroundWorker = new BackGroundWorker(this);
        backGroundWorker.execute("register", uName, email, phno, uPass);
    }
}
