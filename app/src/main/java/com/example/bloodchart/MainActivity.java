package com.example.bloodchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_signup, btn_haveaccount;
    private EditText ed_name, ed_account, ed_password;
    private  static String username;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditText
        ed_name = findViewById(R.id.ed_name);
        ed_account = findViewById(R.id.ed_account);
        ed_password = findViewById(R.id.ed_password);
        //Button
        btn_haveaccount = findViewById(R.id.btn_haveaccount);
        btn_signup = findViewById(R.id.btn_signout);

        //DB
        DB = new DBHelper(this);

        //Btn Click
        btn_haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ed_name.getText().toString();
                String account = ed_account.getText().toString();
                String password = ed_password.getText().toString();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(account) || TextUtils.isEmpty(password))
                    Toast.makeText(MainActivity.this, "All field Required", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkaccount = DB.checkaccount(account);
                    if(checkaccount == false){
                        Boolean insert = DB.insertData(username, account, password);
                        Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(MainActivity.this,"User exist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
    }
}