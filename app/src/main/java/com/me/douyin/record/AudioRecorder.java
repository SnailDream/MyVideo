package com.me.douyin.record;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.me.douyin.utils.FileUtils;

/*
 * Copyright (C) 2017 - 2020 alibaba Inc. All Rights Reserved.
 * Description :
 * Attention   :
 *
 * History     : Creation, 2020/6/17, jiquan.ljq@alibaba-inc.com, Create the file
 */
public class AudioRecorder {

    private static final int RECORD_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int SAMPLE_RATE = 44100;//Hz
    private static final int CHANNEL_CONFIG_SINGLE = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_ENCODING_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private boolean mIsRecording = false;
    private String mFileName;
    private int mBufferSize = 2048;
    private AudioRecord mAudioRecord;
    private AudioRecordCallback mRecordCallback;
    private AudioRecorder() {}

    public static AudioRecorder getInstance() {
        return InstanceHolder.mRecorder;
    }

    private static class InstanceHolder {
        static AudioRecorder mRecorder = new AudioRecorder();
    }

    public void setRecordCallback(final AudioRecordCallback recordCallback) {
        mRecordCallback = recordCallback;
    }

    public void setRecordName(String name) {
        mFileName = name;
    }

    public void start() {
        if (mAudioRecord == null) {
            init();
        }
        mAudioRecord.startRecording();

        startSaveData();
    }

    public void stop() {
        mIsRecording = false;
        mAudioRecord.release();
        mAudioRecord = null;
    }

    private void init() {
        mBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG_SINGLE,
                                                   AUDIO_ENCODING_FORMAT);
        mAudioRecord = new AudioRecord(RECORD_SOURCE, SAMPLE_RATE, CHANNEL_CONFIG_SINGLE,
                                       AUDIO_ENCODING_FORMAT, mBufferSize);


    }

    private void startSaveData() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                saveAudioData();
            }
        });
    }

    private void saveAudioData() {
        File file = new File(mFileName);
        if (file.exists()) {
            file.delete();
        }

        byte[] readBuffer = new byte[mBufferSize];
        FileOutputStream fos = null;

        mIsRecording = true;
        try {
            fos = new FileOutputStream(file.getAbsolutePath());

            while (mIsRecording) {
                int read = mAudioRecord.read(readBuffer, 0, mBufferSize);
                if (read > 0){
                    fos.write(readBuffer);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            FileUtils.close(fos);
        }

    }

}
