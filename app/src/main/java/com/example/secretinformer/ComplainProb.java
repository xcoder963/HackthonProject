package com.example.secretinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComplainProb extends AppCompatActivity {

    EditText issueTxt;
    Button regIssue;
    String email;
    BackGroundWorker backGroundWorker;
    //dataEncryption denc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_prob);

        SharedPreferences sharedPreferences = getSharedPreferences("RV", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        issueTxt = (EditText) findViewById(R.id.issueTxt);
        regIssue = (Button) findViewById(R.id.regIssue);

        final String key = "0123456789abcdef";
        final String initVector = "abcdef9876543210";
        //denc = new dataEncryption();

        regIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitIssue(email, dataEncryption.encrypt(key, initVector, issueTxt.getText().toString()));
            }
        });
    }

    public void submitIssue(String email, String issue) {
        backGroundWorker = new BackGroundWorker(this);
        backGroundWorker.execute("submitComplain", email, issue);
    }
}
