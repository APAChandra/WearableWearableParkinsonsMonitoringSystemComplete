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

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.wearable.wcldemo.MobileApplication;
import com.example.android.wearable.wcldemo.R;
import com.example.android.wearable.wcldemo.common.Constants;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.google.devrel.wcl.WearManager;
import com.google.devrel.wcl.callbacks.AbstractWearConsumer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static android.os.Environment.getExternalStorageState;

//0import com.example.android.wearable.wcldemo.DatabaseAccess;

/**
 * A fragment that receives the database file transferred from the wear app. When the database file is transferred, this fragment
 * reads the content of the file and presents that to the user in a text box.
 */
public class FileTransferFragment extends Fragment {

    private static final String TAG = "FileTransferFragment";
    private WearManager mWearManager;
    private AbstractWearConsumer mWearConsumer;
    private ImageView mImageView;
    private TextView mTextView;
    //private AsyncTask<Void, Void, Bitmap> mAsyncTask;
    private AsyncTask<Void, Void, String> mAsyncTask;
    private ProgressBar mProgressBar;
    private static final int BUFFER_SIZE = 1024;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpWearListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_transfer, container, false);
        mImageView = (ImageView) view.findViewById(R.id.image);
        mTextView = (TextView) view.findViewById(R.id.text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        view.findViewById(R.id.clear_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setImageResource(R.drawable.ic_photo_200dp);
            }
        });
        view.findViewById(R.id.clear_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(R.string.text_file_here);
            }
        });

        return view;
    }

    /**
     * Creates a listener to be called when the transfer of the database file is completed. In some cases, it is
     * desired to transfer files even if the application at the receiving node is not in front.
     * In those cases, one can define the same {@link com.google.devrel.wcl.callbacks.WearConsumer}
     * in the application instance; then the WearableListener that the WCL library provides will be
     * able to handle the transfer.
     */
    private void setUpWearListeners() {
        mWearManager = WearManager.getInstance();
        mWearConsumer = new AbstractWearConsumer() {

            @Override
            public void onWearableInputStreamForChannelOpened(int statusCode, String requestId,
                                                              final Channel channel, final InputStream inputStream) {
                if (statusCode != WearableStatusCodes.SUCCESS) {
                    Log.e(TAG, "onWearableInputStreamForChannelOpened(): "
                            + "Failed to get input stream");
                    return;
                }
                Log.d(TAG, "Channel opened for path: " + channel.getPath());

                mAsyncTask = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        mImageView.setImageResource(R.drawable.ic_photo_200dp);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override

                    /**
                     * This function writes the stream input that it gets from the watch into the file "Wear.db" {@link #file}.
                     */
                    protected String doInBackground(Void... params) {

                        final byte[] buffer = new byte[BUFFER_SIZE];
                        BufferedInputStream in = null;
                        BufferedOutputStream out = null;

                       File file = new File(getContext().getExternalFilesDir(null), "Wear.db");

                        try
                        {
                            in =  new BufferedInputStream(inputStream);
                            out = new BufferedOutputStream(new FileOutputStream(file));
                            while((in.read(buffer))!= -1)
                            {
                                out.write(buffer);


                            }




                        }
                        catch(IOException e)
                        {
                            Log.e(TAG, "Error!",e);
                        }
                        closeStreams();
                        if (isCancelled()) {
                            return null;
                        }

                        return "DB Transfer completed!";
                    }

                    @Override
                    protected void onCancelled() {
                        mProgressBar.setVisibility(View.GONE);
                        mAsyncTask = null;
                        closeStreams();
                    }

                    /**
                     * To verify the file transfer by reading the contents from the "Wear.db" file and show the length
                     * and presence of it.
                     * @param message
                     */
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    protected void onPostExecute(String message) {

                        File file = new File(getContext().getExternalFilesDir(null), "Wear.db");

                         String line;
                        StringBuilder text = new StringBuilder();

                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));



                            while ((line = br.readLine()) != null) {
                                text.append(line);

                            }
                            br.close();
                        }
                        catch (IOException e) {
                            //You'll need to add proper error handling here
                        }


                        if (file != null) {
                            mTextView.setText(String.valueOf(file.length()) + " " + getExternalStorageState(file) +  "DB Transfer completed" + String.valueOf(file.exists()) + text);

                        }



                        mAsyncTask = null;



                    }

                    public void closeStreams() {
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            // no-op
                        }
                    }
                };
                mAsyncTask.execute();
            }

            @Override
            public void onWearableFileReceivedResult(int statusCode, String requestId,
                                                     File savedFile, String originalName) {
                Log.d(TAG, String.format(
                        "File Received: status=%d, requestId=%s, savedLocation=%s, originalName=%s",
                        statusCode, requestId, savedFile.getAbsolutePath(), originalName));
                String fileContent = getSimpleTextFileContent(savedFile);
                mTextView.setText(fileContent);
            }

        };
    }

    /**
     * A rudimentary method to read the content of the {@code file}.
     */
    private String getSimpleTextFileContent(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("file is null or doesn't exists!");
        }
        try {
            return new Scanner(file).useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            // already captured
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

        // register our listeners
        mWearManager.addWearConsumer(mWearConsumer);

        // add the local capability to handle file transfer
        mWearManager.addCapabilities(Constants.CAPABILITY_FILE_PROCESSOR);

        MobileApplication.setPage(Constants.TARGET_FILE_TRANSFER);
    }

    @Override
    public void onPause() {
        // unregister our listeners
        mWearManager.removeWearConsumer(mWearConsumer);

        // remove the capability to handle file transfer
        mWearManager.removeCapabilities(Constants.CAPABILITY_FILE_PROCESSOR);

        if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
            mAsyncTask = null;
        }
        super.onPause();
    }
}
