package com.example.android.wearable.wcldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.wearable.wcldemo.pages.FileTransferFragment;


/**
 * This method is for the main page of the Wearable App after Login and submitting history.
 * It displays the list of tests for the objective quantification of
 * ET symptoms: tremor, bradykinesia, ataxia, and cognitive dysfunctions.
 * Once the test is selected, respected page will be opened
 */


public class TestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        // Find the View that shows the Tremor category
        TextView tremor = (TextView) findViewById(R.id.tvTremor);

        // Set a click listener on that View
        tremor.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, TremorActivity.class));
            }
        });

        // Find the View that shows the Bradykinesia category
        TextView bradykinesia = (TextView) findViewById(R.id.tvBradykinesia);

        // Set a click listener on that View
        bradykinesia.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, BradykinesiaActivity.class));
            }
        });

        // Find the View that shows the balance category
        TextView balance = (TextView) findViewById(R.id.tvBalance);

        // Set a click listener on that View
        balance.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, FileTransferFragment.class));
               /* Fragment fragment = new FileTransferFragment();

                if(fragment != null) {
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.add(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
                else
                {
                    String yo= "no frag";
                    Log.d("NO","FRAG");

                    //Toast.makeText("Centered", Toast.LENGTH_SHORT).show();

                }
*/
            }
        });

        TextView file = (TextView) findViewById(R.id.tvFile);

        // Set a click listener on that View
        file.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(TestListActivity.this, FileTransferFragment.class));
                Fragment fragment = new FileTransferFragment();

                if(fragment != null) {
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.add(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
                else
                {
                    String yo= "no frag";
                    Log.d("NO","FRAG");

                    //Toast.makeText("Centered", Toast.LENGTH_SHORT).show();

                }

            }
        });

        // Find the View that shows the gait category
        TextView gait = (TextView) findViewById(R.id.tvGait);

        // Set a click listener on that View
        gait.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(TestListActivity.this, GaitActivity.class));
            }
        });

        // Find the View that shows the cognitive category
        TextView cognitive = (TextView) findViewById(R.id.tvCognitive);

        // Set a click listener on that View
        cognitive.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestListActivity.this, CognitiveActivity.class));
            }
        });
    }
}
