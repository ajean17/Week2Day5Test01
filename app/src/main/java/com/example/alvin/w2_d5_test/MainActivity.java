package com.example.alvin.w2_d5_test;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY_EXTRA = "com.example.alvin.w2_d5_test.MAIN_ACTIVITY_EXTRA";
    EditText et_upDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_upDel = (EditText) findViewById(R.id.et_del_up);
    }

    public void toSaveActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SaveActivity.class);
        startActivity(intent);
    }

    public void toReadActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(intent);
    }

    public void toUpdateActivity(View view) {
        String title = et_upDel.getText().toString();
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra(MAIN_ACTIVITY_EXTRA, title);
        startActivity(intent);
    }

    public void toDeleteActivity(View view) {
        String title = et_upDel.getText().toString();
        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        intent.putExtra(MAIN_ACTIVITY_EXTRA, title);
        startActivity(intent);
    }
}
