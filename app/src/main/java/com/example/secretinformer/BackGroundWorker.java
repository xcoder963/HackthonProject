package com.example.secretinformer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class BackGroundWorker extends AsyncTask<String, Void, String> {

    InternalDatabase internalDatabase = null;
    Intent intent = null;
    Context context; Toast toast;
    webInteractor webIntr;
    AlertDialog.Builder builder; AlertDialog alert;
    String output = null, actionToBePerformed = null, name = null;

    public BackGroundWorker(Context cntx) {
        context = cntx;
        webIntr = new webInteractor();
        internalDatabase = new InternalDatabase(context);
    }

    @Override
    protected String doInBackground(String... params) {
        actionToBePerformed = params[0];
        switch (params[0]) {
            case "login":
                name = params[1];
                output = webIntr.doServerInteraction("login", params[1], params[2]);
                break;
            case "register":
                //get output here
                output = webIntr.doServerInteraction("register", params[1], params[2], params[3], params[4]);
                internalDatabase.insertData(params[1], params[4]);
                break;
            case "submitComplain":
                output = webIntr.doServerInteraction("submitComplain", params[1], params[2]);
                break;
            case "getComplainReviews":
                break;
        }
        return output;
    }

    @Override
    protected void onPreExecute() {
        toast = Toast.makeText(context, "ACTION SUCCESFULL", Toast.LENGTH_LONG);
        builder = new AlertDialog.Builder(context);
    }

    @Override
    protected void onPostExecute(String str) {
        System.out.println(output);
        Boolean isActivityShiftNeeded = false;
        if (output.equals("success")) {
            switch (actionToBePerformed) {
                case "register":
                    intent = new Intent(context, Login.class);
                    isActivityShiftNeeded = true;
                    break;
                case "login":
                    intent = new Intent(context, MainActivity.class);
                    isActivityShiftNeeded = true;
                    intent.putExtra("name", name);
                    break;
                case "submitComplain":
                    intent = new Intent(context, MainActivity.class);
                    isActivityShiftNeeded = true;
                    break;
                /*case "getComplainReviews":
                    isActivityShiftNeeded = false;
                    //write some code to get the json from the server
                    break;*/
            }
            if (isActivityShiftNeeded) {
                builder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        }).setMessage(str);
                alert = builder.create();
                toast.show();
                alert.show();
            }
        } else if (output.equals("unsuccess")) {
            Toast.makeText(context, "ACTION SUCCESS", Toast.LENGTH_LONG).show();
        } else {
            //will use else for now will get first 5 chracters later from json

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
