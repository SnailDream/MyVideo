package com.me.douyin;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.me.douyin.record.AudioPlayer;
import com.me.douyin.record.AudioRecordHelper;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final int RECORD_AUDIO_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {

                if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.RECORD_AUDIO)) {
                    AudioRecordHelper.startRecord(MainActivity.this);
                } else {
                    EasyPermissions.requestPermissions(new
                                                         PermissionRequest.Builder(MainActivity.this
                      , RECORD_AUDIO_REQUEST_CODE, Manifest.permission.RECORD_AUDIO)
                                                         .setPositiveButtonText("ok")
                                                         .setNegativeButtonText("cancel")
                                                         .build());
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                AudioRecordHelper.stopRecord();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                AudioPlayer.doPlay(MainActivity.this,
                                   new File(AudioRecordHelper.getAudioFilePath(MainActivity.this)));
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {

            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(final int requestCode, @NonNull final List<String> perms) {
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            AudioRecordHelper.startRecord(MainActivity.this);
        }
    }

    @Override
    public void onPermissionsDenied(final int requestCode, @NonNull final List<String> perms) {

    }
}
