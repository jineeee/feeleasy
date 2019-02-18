package com.feeleasy.project.feeleasy;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class InsertDB extends AsyncTask<String, Void, String> {
    ProgressDialog loading;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String Name = params[0];
            String Tel = params[1];
            String UFur = params[2];
            String IsResi = params[3];
            String Agree = params[4];
            String DefLight = params[5];
            String DefFur = params[6];

            String link = "http://192.168.35.159/test/join.php";
            String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
            data += "&" + URLEncoder.encode("tel", "UTF-8") + "=" + URLEncoder.encode(Tel, "UTF-8");
            data += "&" + URLEncoder.encode("uFur", "UTF-8") + "=" + URLEncoder.encode(UFur, "UTF-8");
            data += "&" + URLEncoder.encode("isResi", "UTF-8") + "=" + URLEncoder.encode(IsResi, "UTF-8");
            data += "&" + URLEncoder.encode("agree", "UTF-8") + "=" + URLEncoder.encode(Agree, "UTF-8");
            data += "&" + URLEncoder.encode("defLight", "UTF-8") + "=" + URLEncoder.encode(DefLight, "UTF-8");
            data += "&" + URLEncoder.encode("defFur", "UTF-8") + "=" + URLEncoder.encode(DefFur, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("1 Exception: " + e.getMessage());
        }
    }

}