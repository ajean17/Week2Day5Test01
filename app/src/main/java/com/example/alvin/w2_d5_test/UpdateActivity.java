package com.example.alvin.w2_d5_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alvin.w2_d5_test.FeedReaderContract.FeedEntry;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private DBHelper helper;
    private SQLiteDatabase database;
    private Button updateBtn;
    EditText titleET;
    EditText contentET;
    TextView resultTVUpdate;
    String titleToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        titleET = (EditText) findViewById(R.id.et_titleUpdate);
        contentET = (EditText) findViewById(R.id.et_contentUpdate);
        updateBtn = (Button) findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(this);
        resultTVUpdate = (TextView) findViewById(R.id.tv_result_update);

        Intent intent = getIntent();
        if(intent != null) {
            titleToUpdate = intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private void updateRecord() {
        resultTVUpdate.setText(R.string.lbl_read_result);
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, titleET.getText().toString());
        values.put(FeedEntry.COLUMN_NAME_CONTENT, contentET.getText().toString());

        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String [] selectionArgs = {
                titleToUpdate
        };

        int count = database.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        String updatedResult;
        if(count > 0) {
            updatedResult = "\nUpdated records: " + count;
            Log.d(TAG, updatedResult);
        } else {
            updatedResult = "\nRecords not updated.";
            Log.d(TAG, updatedResult);
        }
        resultTVUpdate.append(String.format(getString(R.string.lbl_result), updatedResult));
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_update:
                updateRecord();
                break;
        }
    }
}
