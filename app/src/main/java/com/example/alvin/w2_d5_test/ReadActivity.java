package com.example.alvin.w2_d5_test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alvin.w2_d5_test.FeedReaderContract.FeedEntry;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private DBHelper helper;
    private SQLiteDatabase database;
    Button readBtn;
    TextView resultTVRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        resultTVRead = (TextView) findViewById(R.id.tv_result_read);
        readBtn = (Button) findViewById(R.id.btn_read);
        readBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private void readRecord() {
        resultTVRead.setText(R.string.lbl_read_result);
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_CONTENT
        };
        String sortOrder = FeedEntry.COLUMN_NAME_CONTENT + "DESC";
        Cursor cursor = database.query( //**Requires 7 parameters**
                FeedEntry.TABLE_NAME,   //Table
                projection,             //Projection
                null,                   //Selection (WHERE)
                null,                   //Values for selection
                null,                   //Group by
                null,                   //Filters
                null                    //Sort Order
        );

        while(cursor.moveToNext()) {
            long entryID = cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry._ID));
            String entryTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE));
            String entryContent = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_CONTENT));
            String entryResult = "\nNote ID: " + entryID + "   Title: " + entryTitle + "   Content: " + entryContent;
            Log.d(TAG, entryResult);
            resultTVRead.append(String.format(getString(R.string.lbl_result), entryResult));
        }
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_read:
                readRecord();
                break;
        }
    }
}
