package com.example.android.wearable.wcldemo;

/**
 * Created by rajvardhan on 11/29/17.
 * This class is used to initialise the database to be used for recording sensor values.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static java.security.AccessController.getContext;

/**
 * Created by rajvardhan on 11/8/17.
 */

public class SensorDataDbHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "sensorData.db";

   // File file = new File(context.getExternalFilesDir(null), "sensorData.db");


    private static final int DATABASE_VERSION = 1;  //force users to update app

    public SensorDataDbHelper(Context context){ //source of error
        super(context, DATABASE_NAME, null, DATABASE_VERSION);//cursor activity =null

        Log.d("DB", String.valueOf(context.getDatabasePath("sensorData.db")));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      /*  try {
            SensorDataDbHelper.DATABASE_NAME = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + SensorDataContract.SensorDataEntry.TABLE_NAME
                + " ( " + SensorDataContract.SensorDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_X + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Y + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Z + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_X + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Y + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Z + " REAL NOT NULL, " +
                SensorDataContract.SensorDataEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                " ); ";

        Log.d("TABLE_CREATE_Q",SQL_CREATE_WAITLIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + SensorDataContract.SensorDataEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
