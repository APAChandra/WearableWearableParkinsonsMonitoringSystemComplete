package com.example.android.wearable.wcldemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.wearable.wcldemo.helper.DatabaseHistoryHandler;

/**
 * Created by disha on 11/19/2017.
 * This class gets the User Demographics and Medical History
 * pertaining to their age, gender, race, height, weight, socioeconomic status,
 * and education as well as their general health, any diagnosed disease,
 * medication, and substance and alcohol use, and stores in the database
 */


public class HistoryActivity extends Activity{

    private EditText uAge;
    private EditText uGender;
    private EditText uRace;
    private EditText uHeightFt;
    private EditText uHeightIn;
    private EditText uWeight;
    private EditText uStatus;
    private EditText uEducation;
    private EditText uHealth;
    private EditText udiagonisedDisease;
    private EditText uMedication;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String  age, gender, race, height, weight,
            status, education, health, diagonisedDisease, medication, alcoholUse;

    private Button btnSubmitHistory;
    private ProgressDialog logDialog;
    private DatabaseHistoryHandler db;
    private static final String TAG = "MyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.history);

        uAge = (EditText) findViewById(R.id.edtAge);
        uGender = (EditText) findViewById(R.id.edtGender);
        uRace = (EditText) findViewById(R.id.edtRace);
        uHeightFt = (EditText) findViewById(R.id.edtHeightFt);
        uHeightIn = (EditText) findViewById(R.id.edtHeightIn);
        uWeight = (EditText) findViewById(R.id.edtWeight);
        uStatus = (EditText) findViewById(R.id.edtStatus);
        uEducation = (EditText) findViewById(R.id.edtEducation);
        uHealth = (EditText) findViewById(R.id.edtHealth);
        udiagonisedDisease = (EditText) findViewById(R.id.edtDiagDisease);
        uMedication = (EditText) findViewById(R.id.edtMedication);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btnSubmitHistory = (Button) findViewById(R.id.btnSubmit);
        logDialog = new ProgressDialog(HistoryActivity.this);
        logDialog.setCancelable(false);
        db = new DatabaseHistoryHandler(getApplicationContext());


        btnSubmitHistory.setOnClickListener(new Button.OnClickListener() { //Callback method for onclick
            public void onClick(View v) {

                age = uAge.getText().toString().trim();
                gender = uGender.getText().toString().trim();
                race = uRace.getText().toString().trim();
                height = uHeightFt.getText().toString().trim()+ "' " + uHeightIn.getText().toString().trim() + "\"";
                weight = uWeight.getText().toString().trim();
                status = uStatus.getText().toString().trim();
                education = uEducation.getText().toString().trim();
                health = uHealth.getText().toString().trim();
                diagonisedDisease = udiagonisedDisease.getText().toString().trim();
                medication = uMedication.getText().toString().trim();

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                alcoholUse = "";
                if (radioButton != null)
                    alcoholUse = radioButton.getText().toString().trim();


                if ((!age.equals("")) && (!gender.equals("")) && (!race.equals(""))
                        && (!height.equals("")) && (!weight.equals("")) && (!status.equals("")) && (!education.equals(""))
                        && (!health.equals("")) && (!diagonisedDisease.equals("")) && (!medication.equals("")) && (!alcoholUse.equals(""))) {
                    new addHistory().execute();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Enter all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**On clicking Skip Button, {@link TestListActivity} page
         * is launched
         */
        final Button Skip = (Button) findViewById(R.id.btnSkip);
        Skip.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
                startActivity(intent);
            }
        });
    }

    /*Async Task to Send data to server.*/
    private class addHistory extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            logDialog = new ProgressDialog(HistoryActivity.this);
            //nDialog.setTitle("Checking Network");
            logDialog.setMessage("Submitting..");
            logDialog.setIndeterminate(false);
            logDialog.setCancelable(false);
            logDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            db.addUserHistory(age, gender, race, height, weight,
                    status, education, health, diagonisedDisease, medication, alcoholUse);
            Log.i(TAG, "-Added User History.");
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (logDialog.isShowing())
                logDialog.dismiss();
            Log.i(TAG, "Launching TestList activity.");
            Intent intent = new Intent(getApplicationContext(), TestListActivity.class);
            startActivity(intent);
        }
    }
}
