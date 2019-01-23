package com.camp.bit.todolist.db;

import android.provider.BaseColumns;

import com.camp.bit.todolist.beans.State;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量

    private TodoContract() {
    }

    public static class TodoEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_STATE = "state";
        //public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_PRIORITY = "priority";
        public static final String COLUMN_NAME_DATE = "date";
    }

}
