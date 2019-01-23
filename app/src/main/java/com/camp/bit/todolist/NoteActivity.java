package com.camp.bit.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.camp.bit.todolist.beans.Note;
import com.camp.bit.todolist.db.TodoContract;
import com.camp.bit.todolist.db.TodoDbHelper;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;

    private RadioGroup priorityRadioGroup;
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    TodoDbHelper mDbHelper;
    SQLiteDatabase db;
    private boolean isNew = false;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        mDbHelper = new TodoDbHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();

        priorityRadioGroup = findViewById(R.id.rg_priority);
        radioButton0 = findViewById(R.id.rb_0);
        radioButton1 = findViewById(R.id.rb_1);
        radioButton2 = findViewById(R.id.rb_2);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean saveNote2Database(String content) {
        // TODO 插入一条新数据，返回是否插入成功
        Date date = new Date();
        long dateTime = date.getTime();

        int priority;
        int checkedID = priorityRadioGroup.getCheckedRadioButtonId();
        if(checkedID == R.id.rb_0){
            priority = 0;
        }else if (checkedID == R.id.rb_1){
            priority = 1;
        }else if (checkedID == R.id.rb_2){
            priority = 2;
        }else{
            priority = 0;
        }

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_NAME_CONTENT, content);
        values.put(TodoContract.TodoEntry.COLUMN_NAME_STATE, 0);
        values.put(TodoContract.TodoEntry.COLUMN_NAME_DATE, dateTime);
        values.put(TodoContract.TodoEntry.COLUMN_NAME_PRIORITY, priority);

        long newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);

        return newRowId != -1;
    }
}
