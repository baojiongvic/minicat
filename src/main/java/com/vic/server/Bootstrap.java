package com.vic.server;

import com.sun.deploy.net.HttpUtils;
import com.vic.server.util.HttpProtocolUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author vic
 * @date 2021/12/28 22:10
 **/
public class Bootstrap {

    public static final int PORT = 8080;

    /**
     * 启动
     * 监听端口
     */
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("server started on port: " + PORT);

        // 初步功能接受请求返回固定字符串
        /*while(true) {
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            String result = "Hello, Minicat ! ";
            String response = HttpProtocolUtils.getHttpHeaderSuccess(result.getBytes().length) + result;
            outputStream.write(response.getBytes());
            socket.close();
        }*/

        // 根据请求路径返回指定静态资源
        while (true) {
            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            response.writeHtmlResource(request.getUrl());
            socket.close();
        }


    }

    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
