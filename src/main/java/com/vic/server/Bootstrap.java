package com.vic.server;

import com.vic.server.servlet.HttpServlet;
import com.vic.server.servlet.Servlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author vic
 * @date 2021/12/28 22:10
 **/
public class Bootstrap {

    public static final int PORT = 8080;

    private static Map<String, Servlet> servletMap = new HashMap<>();

    /**
     * 启动
     * 监听端口
     */
    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("server started on port: " + PORT);


        loadServlet();
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
        /*while (true) {
            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            response.writeHtmlResource(request.getUrl());
            socket.close();
        }*/

        // 请求动态资源servlet
        /*while (true) {
            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            Servlet servlet = servletMap.get(request.getUrl());
            if (servlet != null) {
                servlet.service(request, response);
            } else {
                response.writeHtmlResource(request.getUrl());
            }
            socket.close();
        }*/

        // 多线程，未使用线程池
        /*while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor processor = new RequestProcessor(socket, servletMap);
            processor.start();
        }*/

        // 多线程，使用线程池
        // 定义线程池
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);

        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor processor = new RequestProcessor(socket, servletMap);
            executorService.execute(processor);
        }

    }

    private void loadServlet() throws Exception {
        InputStream is = Bootstrap.class.getClassLoader().getResourceAsStream("web.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        Element root = document.getRootElement();
        List<Element> selectNodes = root.selectNodes("//servlet");
        selectNodes.forEach(element -> {
            String servletName = element.selectSingleNode("servlet-name").getStringValue();
            String servletClass = element.selectSingleNode("servlet-class").getStringValue();

            Element servletMappingElement = (Element) root.selectSingleNode("/web-app/servlet-mapping[servlet-name='" +
                    servletName + "']");
            String urlPattern = servletMappingElement.selectSingleNode("url-pattern").getStringValue();
            try {
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
