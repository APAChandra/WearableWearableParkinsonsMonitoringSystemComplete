package com.example.android.wearable.wcldemo;

/**
 * Created by rajvardhan on 11/29/17.
 * This class defines the columns in the sensor readings database.
 */
import android.provider.BaseColumns;

/**
 * Created by rajvardhan on 11/8/17.
 */

public class SensorDataContract {

    public static final class SensorDataEntry implements BaseColumns {

        public static final String TABLE_NAME = "sensorData";
        public static final String COLUMN_ACCELEROMETER="accelerometerMagnitude";
        public static final String COLUMN_ACCELEROMETER_X="accelerometerX";
        public static final String COLUMN_ACCELEROMETER_Y="accelerometerY";
        public static final String COLUMN_ACCELEROMETER_Z="accelerometerZ";
        public static final String COLUMN_GYROSCOPE ="gyroscopeMagnitude";
        public static final String COLUMN_GYROSCOPE_X ="gyroscopeX";
        public static final String COLUMN_GYROSCOPE_Y ="gyroscopeY";
        public static final String COLUMN_GYROSCOPE_Z ="gyroscopeZ";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

}
