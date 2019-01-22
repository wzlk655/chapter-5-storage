package com.camp.bit.todolist.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.camp.bit.todolist.R;
import com.camp.bit.todolist.beans.Note;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created on 2019/1/23.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);

    private CheckBox checkBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        checkBox = itemView.findViewById(R.id.checkbox);
        contentText = itemView.findViewById(R.id.text_content);
        dateText = itemView.findViewById(R.id.text_date);
        deleteBtn = itemView.findViewById(R.id.btn_delete);
    }

    public void bind(Note note) {
        contentText.setText(note.getContent());
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));
    }
}
