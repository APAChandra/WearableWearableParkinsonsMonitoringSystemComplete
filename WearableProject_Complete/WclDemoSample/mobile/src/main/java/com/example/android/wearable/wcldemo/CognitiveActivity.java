package com.example.android.wearable.wcldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;


/**
 * This cass displays the instruction for Cognitive assessment: Color-Shape test,
 * and provides a start test button. On clicking, CognitiveColorShapeTestActivity page is opened
 */

public class CognitiveActivity extends AppCompatActivity {

    private static WebView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cognitive_activity);
        ImageView play = (ImageView) findViewById(R.id.playButton);


        instructions = (WebView) findViewById(R.id.instruct);

        String htmlText = " %s ";
        String myData = "<html><body  style=\"text-align:justify;\">"
        + "1. Posture: Place the smartphone on a flat table, while sitting.<br /> "
        + "2. You will see paired colors and shapes which appear at the top of the screen and serve as a legend for the task.<br /> "
        + "3. At the bottom of the screen are colored pads which correspond to colors in the legend.<br /> "
        + "4. When the task is initiated, random shapes from the legend appear one at a time in the center of the screen.<br /> "
        + "5. Choose the appropriate color from the bottom.<br /> "
        + "</body></html>";

        instructions.loadData(String.format(htmlText, myData), "text/html", "utf-8");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CognitiveActivity.this, CognitiveColorShapeTestActivity.class));
                finish();
            }
        });
    }
}
