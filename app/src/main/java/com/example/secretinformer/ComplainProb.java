package com.example.secretinformer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComplainProb extends AppCompatActivity {

    EditText issueTxt;
    Button regIssue;
    String email;
    BackGroundWorker backGroundWorker;
    LocationManager locationManager;
    //dataEncryption denc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_prob);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            turnOnGPS();
        }

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
                String[] locationValue = getLocation();
                submitIssue(email, dataEncryption.encrypt(key, initVector, issueTxt.getText().toString()),locationValue[0], locationValue[1]);
            }
        });
    }

    private void turnOnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public String[] getLocation() {
        double lat = 0;
        double longi = 0;
        if (ActivityCompat.checkSelfPermission(
                ComplainProb.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ComplainProb.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                lat = locationGPS.getLatitude();
                longi = locationGPS.getLongitude();
            }
        }
        return (new String[] {String.valueOf(lat), String.valueOf(longi)});
    }

    public void submitIssue(String email, String issue, String lat, String longi) {
        backGroundWorker = new BackGroundWorker(this);
        backGroundWorker.execute("submitComplain", email, issue, lat, longi);
    }
}
