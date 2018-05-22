package com.grandgroup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.grandgroup.model.EventsModel;
import com.grandgroup.utills.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class SQLiteQueries {
    private static SQLiteQueries mInstance = null;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    private SQLiteQueries(Context _context) {
        databaseHelper = new DatabaseHelper(_context, AppConstant.DATABASE_NAME, null, AppConstant.DATABASE_VERSION);
    }

    public static SQLiteQueries getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SQLiteQueries(context.getApplicationContext());
        }
        return mInstance;
    }

    private SQLiteQueries open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void saveEvent(String event_date, String event_title, String event_desc) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConstant.EVENTDATE, event_date);
        contentValues.put(AppConstant.EVENTTITLE, event_title);
        contentValues.put(AppConstant.EVENTSDESC, event_desc);

        database.insert(AppConstant.EVENTSTABLE, null, contentValues);
    }

    public List<EventsModel> getEvents(String date) {
        open();
        List<EventsModel> events_list = new ArrayList<>();
        String Query = "SELECT * FROM " + AppConstant.EVENTSTABLE + " where " + AppConstant.EVENTDATE + " = '" + date + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String eventdate = cursor.getString(cursor.getColumnIndex(AppConstant.EVENTDATE));
                String eventtitle = cursor.getString(cursor.getColumnIndex(AppConstant.EVENTTITLE));
                String eventdesc = cursor.getString(cursor.getColumnIndex(AppConstant.EVENTSDESC));
                EventsModel eventsModel = new EventsModel(eventdate,eventtitle,eventdesc);
                events_list.add(eventsModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return events_list;
    }

}
