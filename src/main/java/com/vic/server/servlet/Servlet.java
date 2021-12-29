package com.vic.server.servlet;

import com.vic.server.Request;
import com.vic.server.Response;

/**
 * @author vic
 * @date 2021/12/29 21:02
 **/
public interface Servlet {

    void init() throws Exception;

    void destroy() throws Exception;

    void service(Request req, Response res) throws Exception;
}
