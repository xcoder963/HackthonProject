package com.example.secretinformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Button issueReg, oldIssues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.issueReg:
                i = new Intent(getBaseContext(), ComplainProb.class);
                startActivity(i);
                break;
            case R.id.oldIssues:
                i = new Intent(getBaseContext(), OldComplains.class);
                startActivity(i);
                break;
            case R.id.quitApp:
                quitToast("CLOSING APP");
                finish();
                break;
        }
    }

    public void quitToast(String str) {
        Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }
}
