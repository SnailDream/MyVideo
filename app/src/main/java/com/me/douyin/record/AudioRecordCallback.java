package com.me.douyin.record;

/*
 * Copyright (C) 2017 - 2020 alibaba Inc. All Rights Reserved.
 * Description :
 * Attention   :
 *
 * History     : Creation, 2020/6/22, jiquan.ljq@alibaba-inc.com, Create the file
 */
public interface AudioRecordCallback {
    void onRecordStart();
    void onRecording(int[] bufferData);
    void onRecordEnd();
}
