package com.example.android.wearable.wcldemo;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class displays Tremor tests such as Rest, Postural, Intention and Kinetic for selecting.
 * It puts a correct mark for the tests that are completed
 */


public class FingerTappingActivity extends AppCompatActivity {

    private TextView IntraLimbCoord, InterLimbCoord, IntraLimbBrady;
    private ImageView IntraLimbCoordCorrect, InterLimbCoordCorrect, IntraLimbBradyCorrect;

    public static final int INTRA_LIMB_COORD = 1;
    public static final int INTER_LIMB_COORD = 2;
    public static final int INTRA_LIMB_BRADY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finger_tapping_list);

        IntraLimbCoord = (TextView) findViewById(R.id.IntraLimbCoord);
        InterLimbCoord = (TextView) findViewById(R.id.InterLimbCoord);
        IntraLimbBrady = (TextView) findViewById(R.id.IntraLimbBrady);

        IntraLimbCoordCorrect = (ImageView) findViewById(R.id.IntraLimbCoordCorrect);
        InterLimbCoordCorrect = (ImageView) findViewById(R.id.InterLimbCoordCorrect);
        IntraLimbBradyCorrect = (ImageView) findViewById(R.id.IntraLimbBradyCorrect);

        // Set a click listener
        IntraLimbCoord.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            public void onClick(View v) {
                startActivityForResult(new Intent(FingerTappingActivity.this, TremorRestActivity.class), INTRA_LIMB_COORD);
            }
        });

        // Set a click listener
        InterLimbCoord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(FingerTappingActivity.this, TremorPosturalActivity.class), INTER_LIMB_COORD);
            }
        });

        // Set a click listener on that View
        IntraLimbBrady.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent(FingerTappingActivity.this, TremorIntentionActivity.class), INTRA_LIMB_BRADY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INTRA_LIMB_COORD && resultCode == RESULT_OK) {
            IntraLimbCoordCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }

        if(requestCode == INTER_LIMB_COORD && resultCode == RESULT_OK) {
            InterLimbCoordCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }

        if(requestCode == INTRA_LIMB_BRADY && resultCode == RESULT_OK) {
            IntraLimbBradyCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }
    }
}
