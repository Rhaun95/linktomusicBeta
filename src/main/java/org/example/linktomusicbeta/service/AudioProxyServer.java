package org.example.linktomusicbeta.service;

import ch.qos.logback.classic.Logger;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.linktomusicbeta.controller.MyMusicController;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

public class AudioProxyServer {
    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(AudioProxyServer.class);


    public static void startProxyServer(String audioUrl) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/audio", exchange -> {

            String requestMethod = exchange.getRequestMethod();

            // HEAD 요청이면 Content-Length 설정하지 않음
            if ("HEAD".equalsIgnoreCase(requestMethod)) {
                exchange.sendResponseHeaders(200, -1); // Content-Length 없음
                exchange.close();
                return;
            }
            URL url = new URL(audioUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            logger.info("Connecting to: " + url);
            logger.info("Response Code: " + conn.getResponseCode());


            exchange.sendResponseHeaders(200, conn.getContentLengthLong());
            InputStream in = conn.getInputStream();
            OutputStream out = exchange.getResponseBody();
            int totalBytesRead = 0;


            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                totalBytesRead += bytesRead;
                out.write(buffer, 0, bytesRead);
                out.flush();
                logger.info("Bytes read: " + totalBytesRead);
            }

            in.close();
            out.close();
        });

        server.start();
        System.out.println("Proxy Server Started: http://localhost:8080/audio");
    }
}
