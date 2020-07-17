package com.me.douyin.record;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.widget.Toast;

import com.me.douyin.utils.FileUtils;

/*
 * Copyright (C) 2017 - 2020 alibaba Inc. All Rights Reserved.
 * Description :
 * Attention   :
 *
 * History     : Creation, 2020/6/22, jiquan.ljq@alibaba-inc.com, Create the file
 */
public class AudioPlayer {

    public static void doPlay(final Context context, final File audioFile) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                playAudio(context, audioFile);
            }
        });
    }

    private static void playAudio(final Context context, final File audioFile) {
        //声音类型，扬声器播放
        int steamType = AudioManager.STREAM_MUSIC;

        //采样频率
        int sampleRate = 44100;

        //MONO 表示单声道 录音输入单声道 播放也使用单声道
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;

        //录音使用16bit 所以播放也使用同样的格式
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        //流模式
        int mode = AudioTrack.MODE_STREAM;

        int mBufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);

        AudioTrack audioTrack = new AudioTrack(steamType, sampleRate, channelConfig, audioFormat,
                                               mBufferSizeInBytes, mode);

        //从文件流中读取数据
        FileInputStream inputStream = null;
        DataInputStream dis = null;
        try{
            inputStream = new FileInputStream(audioFile);
            dis = new DataInputStream(new BufferedInputStream(inputStream));
            int length;
            byte[] buffer = new byte[mBufferSizeInBytes];
            //循环读取数据，写到播放器去播放
            audioTrack.play();
            while((length = dis.read(buffer)) != -1){
                int ret = audioTrack.write(buffer, 0,length);
                switch (ret){
                    case AudioTrack.ERROR:
                    case AudioTrack.ERROR_BAD_VALUE:
                    case AudioTrack.ERROR_INVALID_OPERATION:
                    case AudioTrack.ERROR_DEAD_OBJECT:
                        Toast.makeText(context, "play error ", Toast.LENGTH_LONG).show();
                        return ;
                    default:
                        break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            FileUtils.close(inputStream);
            FileUtils.close(dis);
        }
    }
}
