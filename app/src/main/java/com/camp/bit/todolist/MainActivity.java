package com.camp.bit.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.camp.bit.todolist.beans.Note;
import com.camp.bit.todolist.beans.State;
import com.camp.bit.todolist.db.TodoContract.TodoNote;
import com.camp.bit.todolist.db.TodoDbHelper;
import com.camp.bit.todolist.debug.DebugActivity;
import com.camp.bit.todolist.ui.NoteListAdapter;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;

    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbHelper = new TodoDbHelper(this);
        database = dbHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter();
        recyclerView.setAdapter(notesAdapter);
        
        notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private List<Note> loadNotesFromDatabase() {
        if (database == null) {
            return Collections.emptyList();
        }
        List<Note> result = new LinkedList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(TodoNote.TABLE_NAME,
                    new String[]{TodoNote.COLUMN_CONTENT, TodoNote.COLUMN_DATE,
                            TodoNote.COLUMN_STATE},
                    null, null,
                    null, null,
                    TodoNote.COLUMN_DATE + " DESC");

            while (cursor.moveToNext()) {
                String content = cursor.getString(cursor.getColumnIndex(TodoNote.COLUMN_CONTENT));
                long dateMs = cursor.getLong(cursor.getColumnIndex(TodoNote.COLUMN_DATE));
                int intState = cursor.getInt(cursor.getColumnIndex(TodoNote.COLUMN_STATE));

                Note note = new Note();
                note.setContent(content);
                note.setDate(new Date(dateMs));
                note.setState(State.from(intState));

                result.add(note);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

}
