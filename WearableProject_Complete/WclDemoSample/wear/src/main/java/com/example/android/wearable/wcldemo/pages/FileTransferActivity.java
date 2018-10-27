/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */

package com.example.android.wearable.wcldemo.pages;

import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wearable.wcldemo.R;
import com.example.android.wearable.wcldemo.WearApplication;
import com.example.android.wearable.wcldemo.common.Constants;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.google.devrel.wcl.WearManager;
import com.google.devrel.wcl.callbacks.AbstractWearConsumer;
import com.google.devrel.wcl.connectivity.WearFileTransfer;
import com.google.devrel.wcl.filters.NearbyFilter;
import com.google.devrel.wcl.filters.SingleNodeFilter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import static java.security.AccessController.getContext;

/**
 * An activity that shows two different approaches to transferring files between wear nodes.


 * <p>The low level approach transfers the db file which is specified in the method {@link #fileCheck()}
 * from the wear device to the phone using method and we use {@link WearFileTransfer} apis to open a channel between the two
 * devices first and then open an {@link OutputStream} on the wear device and an {@link InputStream}
 * on the phone (through the channel). Then we simply read chunks of bytes from the db file(fileforDb) and write
 * to the {@code OutputStream} opened on the wear and read the transferred bytes from the
 * {@code InputStream} on the mobile. This approach is more useful for real-time communication to
 * transfer bytes as they become available (for example as the microphone on the watch is recording
 * a voice message). In this sample, we show a progress bar on the wear as we transfer bytes of the
 * db file to the phone.
 *
 * <p>A lot of the complexity involved in the orchestration of this approach is done in the library
 * and is hidden from the developer; developers only request an output stream on one end and will be
 * notified when the channel is established and when an output stream is available on the sender
 * side, and will receive a similar callback on the other end when an input stream is available.
 */
public class FileTransferActivity extends WearableActivity
        implements WearFileTransfer.OnChannelTransferProgressListener {

    private static final String TAG = "MainActivity";
    private static final int BUFFER_SIZE = 1024;
  //  private EditText mTextView;

    // the name of the text file that is in the assets directory and will be transferred across in
    // the "high-level" approach
   // private static final String TEXT_FILE_NAME = "text_file.txt";

    // the resource pointing to the image that we transfer in the "low-level" approach
    private static final int IMAGE_RESOURCE_ID = R.raw.android_wear;///give the db id


    private WearManager mWearManager;
    private AbstractWearConsumer mWearConsumer;
    private ProgressBar mProgressBar;
    private Handler mHandler;
    //private Context context;
   // private File db_file = new File(this.getFilesDir() + "/" + "sensorData.db");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // View view = inflater.inflate(R.layout.file_transfer, container, false);
        //mTextView =  (EditText) findViewById(R.id.file_exists);
        // moving a text file from "assets" directory to the internal app directory so we can get
        // a File reference to that for one of the examples below
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                copyFileToPrivateDataIfNeededAndReturn(TEXT_FILE_NAME);
            }
        }).start();
*/
        mHandler = new Handler();
        setContentView(R.layout.file_transfer);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            }
        });
        mWearManager = WearManager.getInstance();


        // we define a listener to inform us of the status of the file transfer
        mWearConsumer = new AbstractWearConsumer() {
            @Override
            public void onWearableSendFileResult(int statusCode, String requestId) {
                Log.d(TAG, String.format("Status Code=%d, requestId=%s", statusCode, requestId));
            }
        };

        setAmbientEnabled();

    }

    /**
     * To check the if the db file{@link com.example.android.wearable.wcldemo.SensorDataDbHelper#DATABASE_NAME} exists and to
     * verify the length of the db file.
     *
     */
    public void fileCheck()
    {
        File fileforDb = null;
        fileforDb = new File(String.valueOf(this.getDatabasePath("sensorData.db")));

        Log.d("Tag",String.valueOf(fileforDb.exists()) + "  " + String.valueOf(this.getDatabasePath("sensorData.db")) + "  " + String.valueOf(fileforDb.length()));
    }


    public void onClick(View view) {
        // first we try to find at least one nearby connected node
        Set<Node> nodes = mWearManager
                .getNodesForCapability(Constants.CAPABILITY_FILE_PROCESSOR,
                        new SingleNodeFilter(new NearbyFilter()));

        if (nodes != null && !nodes.isEmpty()) {
            Node targetNode = nodes.iterator().next();
            Log.d(TAG, "Targeting node: " + targetNode);

            switch (view.getId()) {

                case R.id.low_level:
                    // the "low-level" approach

                    WearFileTransfer fileTransferLowLevel = new WearFileTransfer.Builder(
                            targetNode)
                            .setOnChannelOutputStreamListener(
                                    new OutputStreamListener(String.valueOf(this.getDatabasePath("sensorData.db")),
                                            FileTransferActivity.this))
                            .build();
                    fileTransferLowLevel.requestOutputStream();
                    break;
            }

        } else {
            Toast.makeText(this, R.string.no_node_available, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A listener that is called when we have a channel open and an {@code OutputStream} ready
     */
    private class OutputStreamListener
            implements WearFileTransfer.OnWearableChannelOutputStreamListener {

        private final WearFileTransfer.OnChannelTransferProgressListener mProgressListener;
        private final String mResource;

        OutputStreamListener(String resourceName,
                WearFileTransfer.OnChannelTransferProgressListener progressListener) {
            mResource = resourceName;
            mProgressListener = progressListener;
        }

        @Override
        public void onOutputStreamForChannelReady(final int statusCode, final Channel channel,
                final OutputStream outputStream) {

            if (statusCode != WearableStatusCodes.SUCCESS) {
                Log.e(TAG, "Failed to open a channel, status code: " + statusCode);
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    fileCheck();
                    final byte[] buffer = new byte[BUFFER_SIZE];
                    BufferedInputStream bis = null;
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    int transferred = 0;
                    int nRead;
                    try {
                        File initialFile = new File(mResource);
                        InputStream is = new FileInputStream(initialFile);
                        //InputStream is = getResources().openRawResource(mResourceId);
                        long fileSize = is.available();
                        bis = new BufferedInputStream(is);
                        while ((nRead = bis.read(buffer)) != -1) {
                            bos.write(buffer);
                            transferred += nRead;
                            if (mProgressListener != null) {
                                mProgressListener.onProgressUpdated(transferred, fileSize);
                            }
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "startTransfer(): IO Error while reading/writing", e);
                        if (mProgressListener != null) {
                            mProgressListener.onProgressUpdated(0, 0);
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FileTransferActivity.this,
                                        R.string.failed_to_transfer_bytes, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    } finally {
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (Exception e) {
                                // ignore
                            }
                        }

                        try {
                            bos.close();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * Called on a non-UI thread
     * This method shows the progress bar as the file is being transferred once the "Send DB" button is pressed.
     */

    @Override
    public void onProgressUpdated(final long progress, final long max) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setMax((int) max);
                mProgressBar.setProgress((int) progress);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();

        // register our listener
        mWearManager.addWearConsumer(mWearConsumer);
        WearApplication.setPage(Constants.TARGET_FILE_TRANSFER);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // remove our listener
        mWearManager.removeWearConsumer(mWearConsumer);
    }

}
