package com.feeleasy.project.feeleasy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup rGrpFur, rGrpUser, rGrpRec;
    int numFur = 0, numUser = 0;
    String n, t, f, r, a = "-1", dl = "-1", df = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        rGrpFur = findViewById(R.id.rgFur);
        rGrpUser = findViewById(R.id.rgUser);
        rGrpRec = findViewById(R.id.rgRec);

        rGrpFur.setOnCheckedChangeListener(this);
        rGrpUser.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if (radioGroup == rGrpFur) {
            switch (i) {
                case R.id.radioFur1:
                case R.id.radioFur2:
                case R.id.radioFur3:
                    findViewById(R.id.linearFur4).setVisibility(View.GONE);
                    numFur = 1;
                    break;
                case R.id.radioFur4:
                    findViewById(R.id.linearFur4).setVisibility(View.VISIBLE);
                    numFur = 2;
                    break;
            }
        } else if (radioGroup == rGrpUser) {
            switch (i) {
                case R.id.radioUser1:
                    findViewById(R.id.linearUser1).setVisibility(View.VISIBLE);
                    findViewById(R.id.linearUser2).setVisibility(View.GONE);
                    numUser = 1;
                    break;
                case R.id.radioUser2:
                    findViewById(R.id.linearUser1).setVisibility(View.GONE);
                    findViewById(R.id.linearUser2).setVisibility(View.VISIBLE);
                    numUser = 2;
                    break;
            }
        }
    }

    public void btnClicked(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnComplete:
                if (getDatas()) {
                    join(n, t, f, r, a, dl, df);
                    login(n);
                }
                break;
        }
    }

    public boolean getDatas() {
        n = ((EditText)findViewById(R.id.editName)).getText().toString();
        if (n.isEmpty()) {
            makeToast("이름을", "입력");
            return false;
        }

        t = ((EditText)findViewById(R.id.editTel)).getText().toString();
        if (t.isEmpty()) {
            makeToast("연락처를", "입력");
            return false;
        }

        switch (numFur) {
            case 1:
                f = ((RadioButton)findViewById(rGrpFur.getCheckedRadioButtonId())).getText().toString();
                break;
            case 2:
                f = ((EditText)findViewById(R.id.editFur4)).getText().toString();
                if (f.isEmpty()) {
                    makeToast("자주 사용하는 가구를", "입력");
                    return false;
                }
                break;
            default:
                makeToast("자주 사용하는 가구를", "선택");
                return false;
        }

        switch (numUser) {
            case 1:  //거주자의 경우
                r = "1";
                int rId = rGrpRec.getCheckedRadioButtonId();
                if (rId == R.id.radioAgree) {
                    a = "1";
                } else if (rId == R.id.radioDisagree) {
                    a = "0";
                } else {
                    makeToast("모니터링 수신 여부를", "선택");
                    return false;
                }
                Editable eDL = ((EditText)findViewById(R.id.editDefLight)).getText();
                if (eDL.length() != 0) {
                    df = eDL.toString();
                }
                Editable eDF = ((EditText)findViewById(R.id.editDefFur)).getText();
                if (eDF.length() != 0) {
                    dl = eDF.toString();
                }
                break;
            case 2:  //보호자의 경우
                r = "0";
                break;
            default:
                makeToast("거주자/보호자 여부를", "선택");
                return false;
        }

        return true;
    }

    public void makeToast(String s, String v) {
        Toast.makeText(JoinActivity.this, s + " " + v + "하세요.", Toast.LENGTH_LONG).show();
    }

    public void join(String name, String tel, String uFur, String isResi, String agree, String defLight, String defFur) {
        InsertDB task = new InsertDB();
        task.execute(name, tel, uFur, isResi, agree, defLight, defFur);
    }

    public void login(String name) {
        class SelectDB extends AsyncTask<String,Void,String> {

            protected void onPreExecute() {}

            @Override
            protected String doInBackground(String... arg0) {

                try {
                    String name =  arg0[0];

                    String link = "http://192.168.35.159/test/login.php?name=" + name;
                    URL url = new URL(link);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    }
                    else{
                        inputStream = httpURLConnection.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader in = new BufferedReader(inputStreamReader);

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                } catch (Exception e) {
                    return new String("2Exception: " + e.getMessage());
                }

            }

            @Override
            protected void onPostExecute(String result){
                Intent intent = new Intent(JoinActivity.this, HomeFragment.class);
                intent.putExtra("uId", result);
                startActivity(intent);
            }

        }

        SelectDB task = new SelectDB();
        task.execute(name);
    }

}