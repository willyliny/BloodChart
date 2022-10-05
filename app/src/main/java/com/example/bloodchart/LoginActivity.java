package com.example.bloodchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login, btn_signup;
    private EditText ed_account, ed_password;
    private String username;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Button
        btn_login = findViewById(R.id.btn_data);
        btn_signup = findViewById(R.id.btn_signup);

        //EditText
        ed_account = findViewById(R.id.ed_account);
        ed_password = findViewById(R.id.ed_password);

        //DB
        DB = new DBHelper(this);

        //Bundle
        Bundle bundle = new Bundle();

        //Click
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = ed_account.getText().toString();
                String password = ed_password.getText().toString();

                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuser = DB.checkuser(account, password);
                    if(checkuser == true){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                        Map<String, String> mapUser = DB.findusermap(account);

                        bundle.putString("username", mapUser.get("username"));
                        bundle.putString("account", mapUser.get("account"));

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}