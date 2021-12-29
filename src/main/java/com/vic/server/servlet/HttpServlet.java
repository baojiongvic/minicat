package com.vic.server.servlet;

import com.vic.server.Request;
import com.vic.server.Response;

/**
 * @author vic
 * @date 2021/12/29 21:03
 **/
public abstract class HttpServlet implements Servlet {

    public static final String GET_METHOD = "GET";

    public abstract void doGet(Request req, Response res);

    public abstract void doPost(Request req, Response res);

    @Override
    public void service(Request req, Response res) throws Exception {
        if (GET_METHOD.equalsIgnoreCase(req.getMethod())) {
            doGet(req, res);
        } else {
            doPost(req, res);
        }
    }
}
