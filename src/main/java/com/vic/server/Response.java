package com.vic.server;

import com.vic.server.util.HttpProtocolUtils;
import com.vic.server.util.StaticResourceUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.*;

/**
 * @author vic
 * @date 2021/12/28 22:36
 **/
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private OutputStream os;

    public void writeResult(String context) throws IOException {
        os.write(context.getBytes());
    }

    public void writeHtmlResource(String path) throws IOException {
        String absoluteResourcePath = StaticResourceUtils.getAbsolutePath(path);

        File file = new File(absoluteResourcePath);
        if (file.exists() && file.isFile()) {
            StaticResourceUtils.outputResource(new FileInputStream(file), os);
        } else {
            writeResult(HttpProtocolUtils.getHttpHeaderNotFound());
        }
    }
}
