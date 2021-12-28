package com.vic.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author vic
 * @date 2021/12/28 22:53
 **/
public class StaticResourceUtils {

    public static String getAbsolutePath(String path) {
        String root = StaticResourceUtils.class.getResource("/").getPath();
        return root.replaceAll("\\\\", "/") + path;
    }

    public static void outputResource(InputStream is, OutputStream os) throws IOException {
        int available = 0;
        while (available == 0) {
            available = is.available();
        }

        int size = available;
        os.write(HttpProtocolUtils.getHttpHeaderSuccess(size).getBytes());

        // 缓存大小，每次读取1024字节
        int buffer = 1024;
        // 已读取长度
        long written = 0;
        byte[] bytes = new byte[buffer];

        while (written < size) {
            // 当剩余长度小于buffer时，重新计算byte数组长度
            if (written + buffer > size) {
                buffer = (int) (size - written);
                bytes = new byte[buffer];
            }

            is.read(bytes);
            os.write(bytes);
            os.flush();

            written += buffer;
        }

    }
}
