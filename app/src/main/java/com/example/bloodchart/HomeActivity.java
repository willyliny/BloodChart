package com.example.bloodchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class HomeActivity extends AppCompatActivity implements InsertDialog.insertDialogListener {
    private Button btn_signup, btn_data, btn_OCR, btn_insert;
    private TextView tv_username;
    public String account, username;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Button
        btn_data = findViewById(R.id.btn_data);
        btn_signup = findViewById(R.id.btn_signup);
        btn_OCR = findViewById(R.id.btn_OCR);
        btn_insert = findViewById(R.id.btn_insert);

        //TextView
        tv_username = findViewById(R.id.tv_name);

        //Bundle
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        account = bundle.getString("account");


        tv_username.setText(username);


        //Click
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertDialog();
            }
        });

    }
    public void openInsertDialog(){
        InsertDialog insertDialog = new InsertDialog();
        insertDialog.show(getSupportFragmentManager(), "insert dialog");
    }

    @Override
    public void applyTexts(String ed_date, String ed_time, String ed_sbp, String ed_dbp){
        DB = new DBHelper(this);
        Boolean insert = DB.insertBPdata(account, ed_date, ed_time,ed_sbp,ed_dbp);
        if(insert == true){
            Toast.makeText(HomeActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            System.out.println("Insert Success");
        }
        else{
            Toast.makeText(HomeActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            System.out.println("Insert Failed");
        }

    }
}