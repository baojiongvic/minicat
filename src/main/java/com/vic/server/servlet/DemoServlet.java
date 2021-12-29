package com.vic.server.servlet;

import com.vic.server.Request;
import com.vic.server.Response;
import com.vic.server.util.HttpProtocolUtils;

import java.io.IOException;

/**
 * @author vic
 * @date 2021/12/29 21:06
 **/
public class DemoServlet extends HttpServlet {

    @Override
    public void doGet(Request req, Response res) {
        try {
            String context = "<h1> Hello World ! GET / 200 </h1>";
            res.writeResult(HttpProtocolUtils.getHttpHeaderSuccess(context.getBytes().length) + context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request req, Response res) {
        try {
            String context = "<h1> Hello World ! POST / 200 </h1>";
            res.writeResult(HttpProtocolUtils.getHttpHeaderSuccess(context.getBytes().length) + context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
