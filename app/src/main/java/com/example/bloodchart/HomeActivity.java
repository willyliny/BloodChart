package com.example.bloodchart;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class HomeActivity extends AppCompatActivity{
    private ImageView imageView;
    private Button btn_signup, btn_data, btn_OCR, btn_csv;
    private TextView tv_username,tv_ocrResult;
    private static  final int REQUEST_CAMERA_CODE = 100;
    public String account, username;
    public ArrayList<String> userRecordData = new ArrayList<String>();
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }



        //Button
        btn_data = findViewById(R.id.btn_data);
        btn_signup = findViewById(R.id.btn_signup);
        btn_OCR = findViewById(R.id.btn_OCR);
        btn_csv = findViewById(R.id.btn_csv);

        //TextView
        tv_username = findViewById(R.id.tv_name);
        tv_ocrResult = findViewById(R.id.tv_ocrResult);
        //Bundle
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        account = bundle.getString("account");

        Bundle bundle2datalist = new Bundle();


        tv_username.setText(username);

        DB = new DBHelper(this);

        //Click
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer csvText = DB.searchUserRecordData(account);
                makeCSV(csvText, account);
            }
        });

        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DatalistActivity.class);
                bundle2datalist.putString("username", username);
                bundle2datalist.putString("account", account);
                intent.putExtras(bundle2datalist);
                startActivity(intent);

            }
        });

        btn_OCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    public void creatCSV(StringBuffer userbpdata){
        new Thread(()->{
            String fileName = username + ".csv";
            StringBuffer csvText = userbpdata;


            Log.d(TAG, "makeCSV: \n"+csvText);//可在此監視輸出的內容
            runOnUiThread(()->{
                try{
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    builder.detectFileUriExposure();

                    FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
                    out.write((csvText.toString().getBytes()));
                    out.close();
                    File fileLocation = new File(Environment.
                            getExternalStorageDirectory().getAbsolutePath(), fileName);
                    FileOutputStream fos = new FileOutputStream(fileLocation);
                    fos.write(csvText.toString().getBytes());
                    Uri path = Uri.fromFile(fileLocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/csv");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, fileName);
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "outputfile"));
                }catch (IOException e){
                    e.printStackTrace();
                    Log.w(TAG,"makeCSV" + e.toString());

                }
            });
        }).start();
    }

    private void makeCSV(StringBuffer userbpdata, String account) {
        new Thread(() -> {
            /**決定檔案名稱*/
            String date = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault()).format(System.currentTimeMillis());
            String fileName = "[" + date + "]" + account + ".csv";
            /**撰寫內容*/
            //以下用詞：直行橫列
            //設置第一列的內容
            String[] title ={"Id","Chinese","English","Math","Physical"};
            StringBuffer csvText = userbpdata;

            Log.d(TAG, "makeCSV: \n"+csvText);//可在此監視輸出的內容
            runOnUiThread(() -> {
                try {
                    //->遇上exposed beyond app through ClipData.Item.getUri() 錯誤時在onCreate加上這行
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    builder.detectFileUriExposure();
                    //->遇上exposed beyond app through ClipData.Item.getUri() 錯誤時在onCreate加上這行
                    FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
                    out.write((csvText.toString().getBytes()));
                    out.close();
                    File fileLocation = new File(Environment.
                            getExternalStorageDirectory().getAbsolutePath(), fileName);
                    FileOutputStream fos = new FileOutputStream(fileLocation);
                    fos.write(csvText.toString().getBytes());
                    Uri path = Uri.fromFile(fileLocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/csv");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, fileName);
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "輸出檔案"));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, "makeCSV: "+e.toString());
                }
            });
        }).start();
    }//makeCSV






}