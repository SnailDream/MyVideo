package com.me.douyin.record;

import android.content.Context;
import android.util.Log;

/*
 * Copyright (C) 2017 - 2020 alibaba Inc. All Rights Reserved.
 * Description :
 * Attention   :
 *
 * History     : Creation, 2020/6/22, jiquan.ljq@alibaba-inc.com, Create the file
 */
public class AudioRecordHelper {

    private static final String TAG = "AudioRecordHelper";
    private static final String TEST_RECORD_NAME = "Test.pcm";

    public static void startRecord(Context context) {
        AudioRecorder.getInstance().setRecordName(context.getExternalCacheDir() + TEST_RECORD_NAME);
        AudioRecorder.getInstance().start();

        Log.i(TAG, "PATH = "+ (context.getExternalCacheDir() + TEST_RECORD_NAME));
    }

    public static void stopRecord() {
        AudioRecorder.getInstance().stop();
    }

    public static String getAudioFilePath(Context context) {
        return context.getExternalCacheDir() + TEST_RECORD_NAME;
    }
}
