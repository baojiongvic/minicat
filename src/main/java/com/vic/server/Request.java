package com.vic.server;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author vic
 * @date 2021/12/28 22:36
 **/
public class Request {

    /**
     * request method GET/POST
     */
    private String method;

    /**
     * url
     */
    private String url;

    /**
     * 输入流
     */
    private InputStream is;

    public Request(InputStream is) throws IOException {
        this.is = is;

        int available = 0;
        while(available == 0) {
            available = is.available();
        }

        byte[] bytes = new byte[available];
        is.read(bytes);

        String input = new String(bytes);
        String firstLine = input.split("\\n")[0];

        String[] firstLineArr = firstLine.split(" ");

        this.method = firstLineArr[0];
        this.url = firstLineArr[1];
    }

    public Request() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
