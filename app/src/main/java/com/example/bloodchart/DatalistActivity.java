package com.example.bloodchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DatalistActivity extends AppCompatActivity implements InsertDialog.insertDialogListener, UpdateDialog.updateDialogListener, DeleteDialog.deleteDialogListener{
    private Button btn_return, btn_insert, btn_update, btn_delete;

    private static final Integer db_id = 0;
    private static final Integer db_name = 1;
    private static final Integer db_date = 2;
    private static final Integer db_time = 3;
    private static final Integer db_sbp = 4;
    private static final Integer db_dbp = 5;
    public String account, username;
    private RecycleAdapter recycleAdapter;
    private RecyclerView recyclerView;
    private DividerItemDecoration myDivider;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> id, date, time, sbp, dbp;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);

        //Button
        btn_return = findViewById(R.id.btn_return);
        btn_insert = findViewById(R.id.btn_insert);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);

        //Bundle
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        account = bundle.getString("account");

        DB = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        displaydata();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displaydata();
                recycleAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Click
        btn_return.setOnClickListener(view -> {
            Intent intent = new Intent(DatalistActivity.this, HomeActivity.class);
            Bundle bundle2datalist = new Bundle();
            bundle2datalist.putString("username", username);
            bundle2datalist.putString("account", account);
            intent.putExtras(bundle2datalist);
            startActivity(intent);
        });

        btn_insert.setOnClickListener(view -> {
            openInsertDialog();
            displaydata();
            recycleAdapter.notifyDataSetChanged();

        });
        btn_update.setOnClickListener(view -> {
            openUpdateDialog();
            displaydata();
            recycleAdapter.notifyDataSetChanged();

        });

        btn_delete.setOnClickListener(view -> {
            opendeleteDialog();
            displaydata();
            recycleAdapter.notifyDataSetChanged();
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
            Toast.makeText(DatalistActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            System.out.println("Insert Success");
        }
        else{
            Toast.makeText(DatalistActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            System.out.println("Insert Failed");
        }
    }

    public void openUpdateDialog(){
        UpdateDialog updateDialog = new UpdateDialog();
        updateDialog.show(getSupportFragmentManager(), "update dialog");
    }

    @Override
    public void updateTexts(String ed_id, String ed_date, String ed_time, String ed_sbp, String ed_dbp) {
        DB = new DBHelper(this);
        Boolean update = DB.updateBPdata(ed_id, ed_date, ed_time, ed_sbp, ed_dbp);
        if(update == true){
            Toast.makeText(DatalistActivity.this, "update Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            System.out.println("update Success");
        }
        else{
            Toast.makeText(DatalistActivity.this, "update Failed", Toast.LENGTH_SHORT).show();
            System.out.println("update Failed");
        }
    }

    public void opendeleteDialog(){
        DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.show(getSupportFragmentManager(), "update dialog");
    }

    @Override
    public void deleteTexts(String ed_id) {
        DB = new DBHelper(this);
        Boolean delete = DB.deleteBPdata(ed_id);
        if(delete == true){
            Toast.makeText(DatalistActivity.this, "delete Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            System.out.println("delete Success");
        }
        else{
            Toast.makeText(DatalistActivity.this, "delete Failed", Toast.LENGTH_SHORT).show();
            System.out.println("delete Failed");
        }
    }


    private void displaydata(){
        id = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        sbp = new ArrayList<>();
        dbp = new ArrayList<>();

        Cursor cursor = DB.getdata(account);
//        System.out.println(cursor.getString(0));
//        System.out.println(cursor.getString(1));
//        System.out.println(cursor.getString(2));
//        System.out.println(cursor.getString(3));

        if(cursor.getCount() == 0){
            Toast.makeText(DatalistActivity.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst();
            do{
                System.out.println(cursor.getString(db_id));
                System.out.println(cursor.getString(db_date));
                System.out.println(cursor.getString(db_time));
                System.out.println(cursor.getString(db_sbp));
                System.out.println(cursor.getString(db_dbp));
                id.add("ID: " + cursor.getString(db_id));
                date.add("Date: " + cursor.getString(db_date));
                time.add("Time: " + cursor.getString(db_time));
                sbp.add("SBP: " + cursor.getString(db_sbp));
                dbp.add("DBP: " + cursor.getString(db_dbp));
            }while (cursor.moveToNext());
        }
        recycleAdapter = new RecycleAdapter(this, id, date, time, sbp, dbp);
        myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        while (recyclerView.getItemDecorationCount()>0){
            recyclerView.removeItemDecorationAt(0);
        }
        recyclerView.addItemDecoration(myDivider);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recycleAdapter);


    }
}