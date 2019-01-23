package com.camp.bit.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    // TODO 定义数据库名、版本；创建数据库
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TodoList.db";

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ TodoContract.TodoEntry.TABLE_NAME+"("+
            TodoContract.TodoEntry._ID + " INTEGER PRIMARY KEY," +
            TodoContract.TodoEntry.COLUMN_NAME_CONTENT + " TEXT," +
            TodoContract.TodoEntry.COLUMN_NAME_STATE + " TEXT," +
            //TodoEntry.COLUMN_NAME_SUBTITLE + " TEXT," +
            TodoContract.TodoEntry.COLUMN_NAME_PRIORITY + " TEXT," +
            TodoContract.TodoEntry.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoContract.TodoEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
