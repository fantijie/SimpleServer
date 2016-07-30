package com.simple.server;

import com.simple.action.BaseAction;
import com.simple.http.Request;
import com.simple.http.Response;
import com.simple.http.util.HttpProtocolParserUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by liangjie on 16/7/30.
 */
public class AcceptHttpRequestThread extends Thread {







    private ServerSocketChannel serverSocketChannel = null;

    public AcceptHttpRequestThread(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {




        try {

                while (!isInterrupted()) {
                    SocketChannel socketChannel = null;

                    socketChannel = serverSocketChannel.accept();
                    if (socketChannel != null) {
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        socketChannel.read(allocate);
                        HttpProtocolParserUtil httpProtocolParserUtil = new HttpProtocolParserUtil();

                        Request request = httpProtocolParserUtil.parser(allocate);


                        Response response = new Response(socketChannel);


                        BaseAction baseAction = new BaseAction();


                        baseAction.login(request, response);
                        response.write("send");
                        socketChannel.shutdownOutput();
                        socketChannel.close();
                    }


                }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}