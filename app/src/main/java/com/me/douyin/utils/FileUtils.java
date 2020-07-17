package com.me.douyin.utils;

import java.io.Closeable;
import java.io.IOException;

/*
 * Copyright (C) 2017 - 2020 alibaba Inc. All Rights Reserved.
 * Description :
 * Attention   :
 *
 * History     : Creation, 2020/6/22, jiquan.ljq@alibaba-inc.com, Create the file
 */
public class FileUtils {
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
