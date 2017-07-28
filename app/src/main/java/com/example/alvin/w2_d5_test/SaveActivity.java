package com.example.alvin.w2_d5_test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alvin.w2_d5_test.FeedReaderContract.FeedEntry;

public class SaveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private DBHelper helper;
    private SQLiteDatabase database;
    private Button saveBtn;
    EditText titleET;
    EditText contentET;
    TextView resultTVSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        titleET = (EditText) findViewById(R.id.et_titleSave);
        contentET = (EditText) findViewById(R.id.et_contentSave);
        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);
        resultTVSave = (TextView) findViewById(R.id.tv_result_save);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private void saveRecord() {
        resultTVSave.setText(R.string.lbl_read_result);
        String title = titleET.getText().toString(); //"Record title";
        String content = contentET.getText().toString(); //"Record subtitle";

        ContentValues values = new ContentValues();//Prevents things like SQL injection, while allowing for prepared statements;
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_CONTENT, content);

        long recordID = database.insert(
                FeedEntry.TABLE_NAME,
                null,
                values
        );
        String saveResult;
        if(recordID > 0) {
            saveResult = "\nNote saved.";
            Log.d(TAG, saveResult);
        } else {
            saveResult = "\nNote not saved.";
            Log.d(TAG, saveResult);
        }
        titleET.setText("");
        contentET.setText("");
        resultTVSave.append(String.format(getString(R.string.lbl_result), saveResult));
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_save:
                saveRecord();
                break;
        }
    }
}
