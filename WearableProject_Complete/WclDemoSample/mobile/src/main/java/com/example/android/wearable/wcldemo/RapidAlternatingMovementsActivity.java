package com.example.android.wearable.wcldemo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wearable.wcldemo.helper.SensorDataDbHelper;
import com.google.devrel.wcl.WearManager;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_GYROSCOPE;


public class RapidAlternatingMovementsActivity extends AppCompatActivity implements SensorEventListener{

    private static ImageView play;
    private static TextView counter;
    private static WebView instructions;
    private static Thread thread;
    private static AlertDialog alertDialog;
    private float mMagnitude=0;
    private static int mAFlag=0;
    private static int mGFlag=0;
    private static float mAccelerometerMagnitude;
    private static float mAccelerometerX;
    private static float mAccelerometerY;
    private static float mAccelerometerZ;
    private static float mGyroscopeMagnitude;
    private static float mGyroscopeX;
    private static float mGyroscopeY;
    private static float mGyroscopeZ;
    WearManager mWearManager = WearManager.getInstance();
    private static final String WEAR_APP_CAPABILITY = "wear_app_capability";
    private static final String WEAR_ACTIVITY = "com.example.android.wearable.wcldemo.SensorActivity";

    private static final String TAG = "MyActivity";

    private SQLiteDatabase mDb;
    private SensorManager mSensorManager;
    private Sensor mAccelSensor;
    private Sensor mGyroSensor;
    private Integer count = 0;
    TextView textViewInfo;
    ImageView imageView;
    GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapid_alternating_movements);

        gifView = (GifView) findViewById(R.id.gifview);
        gifView.setGifImageResource(R.drawable.armmove);

        //imageView = (ImageView) findViewById(R.id.gifview);
        //Glide.with(this).asGif().load(R.drawable.armmove).into(imageView);

        counter = (TextView) findViewById(R.id.ctrram);
        play =(ImageView) findViewById(R.id.playbtnram);
        instructions = (WebView) findViewById(R.id.instruct);

        String htmlText = " %s ";
        String myData = "<html><body  style=\"text-align:justify;\">";
        myData += "1. Posture: Hold the smartphone in your dominant hand with the screen facing away from the palm, whilst standing with your arm outstretched infront of you.<br /> ";
        myData += "2. Once you are ready Click on below Start Button to start your assessment.<br /> ";
        myData += "3. This test will run for 20sec.<br /> ";
        myData += "</body></html>";

        instructions.loadData(String.format(htmlText, myData), "text/html", "utf-8");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                counter.setVisibility(View.VISIBLE);
                launchMobileApp();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog.Builder(RapidAlternatingMovementsActivity.this)
                                        .setTitle("In progress")
                                        .setMessage("Application is collecting data")
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setCancelable(false)
                                        .show();
                            }
                        });
                        Log.d("Thread", "Started");
                        SensorDataDbHelper dbHelper = new SensorDataDbHelper(RapidAlternatingMovementsActivity.this);
                        mDb = dbHelper.getWritableDatabase();
                        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        mAccelSensor = mSensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
                        mGyroSensor = mSensorManager.getDefaultSensor(TYPE_GYROSCOPE);
                        mSensorManager.registerListener(RapidAlternatingMovementsActivity.this, mAccelSensor, 20);
                        mSensorManager.registerListener(RapidAlternatingMovementsActivity.this, mGyroSensor, 20);
                        while (count < 20) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count++;
                        }
                        mSensorManager.unregisterListener(RapidAlternatingMovementsActivity.this);
                        mDb.close();
                        setResult(RESULT_OK, null);
                        alertDialog.dismiss();
                        finish();
                    }
                });
                thread.start();
            }
        });
    }




    private void launchMobileApp() {
        if (!mWearManager.launchAppOnNodes(WEAR_ACTIVITY, null, false,
                WEAR_APP_CAPABILITY, null)) {
            toastMessage(R.string.failed_to_launch_wear_app);
        }

    }

    private void toastMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // If sensor is unreliable, then just return
        Log.d("onSensorChanged", "Received event " + String.valueOf(event.accuracy));
        /*if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }*/
        mMagnitude = (float)Math.sqrt((float)Math.pow(event.values[0],2)+(float)Math.pow(event.values[1],2)+(float)Math.pow(event.values[2],2));

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ++mAFlag;
            //get accelerometer values
            mAccelerometerMagnitude = mMagnitude;
            mAccelerometerX =event.values[0];
            mAccelerometerY = event.values[1];
            mAccelerometerZ = event.values[2];
            if ((mAFlag==1) && (mGFlag==1)){
                addNewSensorData(mAccelerometerMagnitude,mAccelerometerX, mAccelerometerY, mAccelerometerZ,mGyroscopeMagnitude,mGyroscopeX, mGyroscopeY, mGyroscopeZ);
                mAFlag=0;
                mGFlag=0;
            }
            else{
                if(mAFlag>1){
                    mAFlag=1;
                }
                if(mGFlag>1){
                    mGFlag=1;
                }
            }

        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            ++mGFlag;
            //get gyroscope values
            mGyroscopeMagnitude = mMagnitude;
            mGyroscopeX =event.values[0];
            mGyroscopeY = event.values[1];
            mGyroscopeZ = event.values[2];
            if((mAFlag==1) && (mGFlag==1)){
                addNewSensorData(mAccelerometerMagnitude,mAccelerometerX, mAccelerometerY, mAccelerometerZ,mGyroscopeMagnitude,mGyroscopeX, mGyroscopeY, mGyroscopeZ);
                mAFlag=0;
                mGFlag=0;
            }
            else{
                if(mAFlag>1){
                    mAFlag=1;
                }
                if(mGFlag>1){
                    mGFlag=1;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private long addNewSensorData(float accelerometerMagnitude, float accelerometerX, float accelerometerY, float accelerometerZ,float gyroscopeMagnitude, float gyroscopeX, float gyroscopeY, float gyroscopeZ){
/**
 Log.d(TAG, "-accelerometerMagnitude" +accelerometerMagnitude);
 Log.d(TAG, "-accelerometerX" +accelerometerX);
 Log.d(TAG, "-accelerometerY" +accelerometerY);
 Log.d(TAG, "-accelerometerZ" +accelerometerZ);
 Log.d(TAG, "-gyroscopeMagnitude" +gyroscopeMagnitude);
 Log.d(TAG, "-GyroscopeX" +mGyroscopeX);
 Log.d(TAG, "-GyroscopeY" +mGyroscopeY);
 Log.d(TAG, "-GyroscopeZ" +mGyroscopeZ);
 */
        ContentValues cv = new ContentValues();
        String value = "rapidaltmov";
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_VALUE,value);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER,accelerometerMagnitude);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_X,accelerometerX);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Y,accelerometerY);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_ACCELEROMETER_Z,accelerometerZ);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE,gyroscopeMagnitude);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_X,gyroscopeX);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Y,gyroscopeY);
        cv.put(SensorDataContract.SensorDataEntry.COLUMN_GYROSCOPE_Z,gyroscopeZ);
        Log.d("addNewSensorData", "Added");
        return mDb.insert(SensorDataContract.SensorDataEntry.TABLE_NAME,null,cv);
    }
}
