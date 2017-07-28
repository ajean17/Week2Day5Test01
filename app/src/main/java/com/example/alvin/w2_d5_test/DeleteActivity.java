package com.example.alvin.w2_d5_test;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alvin.w2_d5_test.FeedReaderContract.FeedEntry;
public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private DBHelper helper;
    private SQLiteDatabase database;
    Button deleteBtn;
    TextView resultTVDelete;
    String titleToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        resultTVDelete = (TextView) findViewById(R.id.tv_result_delete);
        deleteBtn = (Button) findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent != null) {
            titleToDelete = intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    public void deleteRecord() {
        resultTVDelete.setText(R.string.lbl_read_result);
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String selectionArgs[] = {
                titleToDelete
        };
        int deleted = database.delete(
                FeedEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        String deleteResult;
        if(deleted > 0) {
            deleteResult = "\nRecord deleted.";
            Log.d(TAG, deleteResult);
        } else {
            deleteResult = "\nRecord not deleted.";
            Log.d(TAG, deleteResult);
        }
        resultTVDelete.append(String.format(getString(R.string.lbl_result), deleteResult));
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_delete:
                deleteRecord();
                break;
        }
    }
}
