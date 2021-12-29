package com.vic.server;

import com.vic.server.servlet.Servlet;

import java.net.Socket;
import java.util.Map;

/**
 * @author vic
 * @date 2021/12/29 21:44
 **/
public class RequestProcessor implements Runnable{

    private Socket socket;

    private Map<String, Servlet> servletMap;

    public RequestProcessor(Socket socket, Map<String, Servlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            Servlet servlet = servletMap.get(request.getUrl());
            if (servlet != null) {
                servlet.service(request, response);
            } else {
                response.writeHtmlResource(request.getUrl());
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
