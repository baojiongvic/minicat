package com.vic.server.util;

/**
 * @author vic
 * @date 2021/12/28 22:22
 **/
public class HttpProtocolUtils {

    /**
     * 200响应头
     *
     * @param contentLength 内容长度
     * @return {@link String}
     */
    public static String getHttpHeaderSuccess(long contentLength) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }

    /**
     * 404响应头
     *
     * @return {@link String}
     */
    public static String getHttpHeaderNotFound() {
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;
    }
}
