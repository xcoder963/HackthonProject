package com.example.secretinformer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;

public class webInteractor {

    String netURL = null, post_data = null, line = "", results = "";

    public String doServerInteraction(String choice, String... params) {
        //will place the string here
        netURL = "";
        switch (choice) {
            case "register":
                try {
                    post_data = URLEncoder.encode("actionType", "UTF-8")+"="+URLEncoder.encode(choice, "UTF-8")+"&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[0], "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(params[1], "UTF-8");
                } catch (UnsupportedEncodingException ue) {
                    ue.printStackTrace();
                }
                break;
            case "login":
                try {
                    post_data = URLEncoder.encode("actionType", "UTF-8")+"="+URLEncoder.encode(choice, "UTF-8")+"&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[0], "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(params[1], "UTF-8");
                } catch (UnsupportedEncodingException ue) {
                    ue.printStackTrace();
                }
                break;
            case "submitComplain":
                try {
                    post_data = URLEncoder.encode("actionType", "UTF-8")+"="+URLEncoder.encode(choice, "UTF-8")+"&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[0], "UTF-8")+"&"+URLEncoder.encode("complain", "UTF-8")+"="+URLEncoder.encode(params[1], "UTF-8");
                } catch (UnsupportedEncodingException ue) {
                    ue.printStackTrace();
                }
                break;
            case "getComplainReviews":
                try {
                    post_data = URLEncoder.encode("actionType", "UTF-8")+"="+URLEncoder.encode(choice, "UTF-8")+"&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[0], "UTF-8");
                } catch (UnsupportedEncodingException ue) {
                    ue.printStackTrace();
                }
                break;
        }

        try {
            URL url = new URL(netURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(post_data);
            bufferedWriter.flush(); bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            while ((line = bufferedReader.readLine()) != null)
                results += line;
            bufferedReader.close(); inputStream.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException me) {
            me.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return results;
    }

}
