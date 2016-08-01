package com.simple.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by liangjie on 16/7/30.
 */
public class Response {


    private boolean ResponseHeaderIsAppend = false;

    private ArrayList<Cookie> cookies = null;


    private StringBuilder outPutStr = null;

    static StringBuilder resp = new StringBuilder();
    static{
        resp.append("HTTP/1.1 200 OK\r\n");
        //resp.append("Date: Fri, 31 Dec 2016 23:59:59 GMT\r\n");
        resp.append("Server: Jerry/0.8.4\r\n");
        resp.append("Connection: keep-alive\r\n");
        resp.append("Content-Type: text/html;\r\n");
        //resp.append("Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n");
        //resp.append("Last-modified: Fri, 09 Aug 2021 14:21:40 GMT\r\n");
        resp.append("Set-Cookie: SessionID=" + UUID.randomUUID().toString() + "\r\n");
        //resp.append("Host:127.0.0.1:8080\r\n");
        //resp.append("Vary:Accept-Encoding\r'n");
        resp.append("\r\n");

    }
    private SocketChannel socketChannel = null;


    public Response(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }


    public void write(String output) throws IOException {

        if (outPutStr == null) {
            outPutStr = new StringBuilder();
        }

        if (!ResponseHeaderIsAppend) {



        }

            outPutStr.append(output);


    }

    public void flush() throws IOException {
        StringBuilder responseResult = new StringBuilder();
        responseResult.append(resp);
        responseResult.append(outPutStr.toString());
        socketChannel.write(ByteBuffer.wrap(responseResult.toString().getBytes(Charset.defaultCharset())));
    }
}
