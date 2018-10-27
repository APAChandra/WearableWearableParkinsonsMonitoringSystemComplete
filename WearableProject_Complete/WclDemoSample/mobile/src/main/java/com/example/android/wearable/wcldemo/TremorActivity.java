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


public class TremorActivity extends AppCompatActivity {

    private TextView restTremor, posturalTremor, intentionTremor, kineticTremor;
    private ImageView restCorrect, posturalCorrect, intentionCorrect, kineticCorrect;

    public static final int REST_TREMOR = 1;
    public static final int POSTURAL_TREMOR = 2;
    public static final int INTENTION_TREMOR = 3;
    public static final int KINETIC_TREMOR = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tremor_activity);

        restTremor = (TextView) findViewById(R.id.tvRest);
        posturalTremor = (TextView) findViewById(R.id.tvPostural);
        intentionTremor = (TextView) findViewById(R.id.tvIntention);
        kineticTremor = (TextView) findViewById(R.id.tvKinetic);

        restCorrect = (ImageView) findViewById(R.id.ivRestCorrect);
        posturalCorrect = (ImageView) findViewById(R.id.ivPosturalCorrect);
        intentionCorrect = (ImageView) findViewById(R.id.ivIntentionCorrect);
        kineticCorrect = (ImageView) findViewById(R.id.ivKineticCorrect);

        // Set a click listener
        restTremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            public void onClick(View v) {
                startActivityForResult(new Intent(TremorActivity.this, TremorRestActivity.class), REST_TREMOR);
            }
        });

        // Set a click listener
        posturalTremor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(TremorActivity.this, TremorPosturalActivity.class), POSTURAL_TREMOR);
            }
        });

        // Set a click listener on that View
        intentionTremor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent(TremorActivity.this, TremorIntentionActivity.class), INTENTION_TREMOR);
            }
        });

        // Set a click listener on that View
        kineticTremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TremorActivity.this, TremorKineticActivity.class), KINETIC_TREMOR);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REST_TREMOR && resultCode == RESULT_OK) {
            restCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }

        if(requestCode == POSTURAL_TREMOR && resultCode == RESULT_OK) {
            posturalCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }

        if(requestCode == INTENTION_TREMOR && resultCode == RESULT_OK) {
            intentionCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }

        if(requestCode == KINETIC_TREMOR && resultCode == RESULT_OK) {
            kineticCorrect.setImageResource(R.drawable.correct_mark);
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_SUP_PIP, 1200);
        }
    }
}
