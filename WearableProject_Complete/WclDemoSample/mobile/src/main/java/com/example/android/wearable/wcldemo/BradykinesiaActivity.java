package com.example.android.wearable.wcldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * This class displays a test list page for
 * Bradykinesia assessment: Rapid alternating movement and Finger Tapping
 * Upon selecting respected activity page will be displayed
 */

public class BradykinesiaActivity extends AppCompatActivity {

    private TextView rapidAltMove, fingerTapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bradykinesia_activity);

        rapidAltMove = (TextView) findViewById(R.id.tvRapidAltMove);
        fingerTapping = (TextView) findViewById(R.id.tvFingerTapping);

        // Set a click listener
        rapidAltMove.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RapidAlternatingMovementsActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Set a click listener
        fingerTapping.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FingerTappingActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
