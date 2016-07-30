package com.simple.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liangjie on 16/7/30.
 */
public class BootStartServer {


    private static AtomicInteger count = new AtomicInteger();

    private static int port = 8080;

    private static  ExecutorService executorService = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());


    public static void main(String[] args) {


        System.out.println("Start server.....");
        System.out.println("port : " + args[0]);
        try {
            ServerSocketChannel serverSocketChannel = null;

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(true);

            serverSocketChannel.socket().bind(new InetSocketAddress(Integer.valueOf(args[0])));

            for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
                AcceptHttpRequestThread acceptHttpRequestThread = new AcceptHttpRequestThread(serverSocketChannel);
                executorService.submit(acceptHttpRequestThread);

            }


            while (!executorService.isShutdown()){
                Thread.currentThread().join(Integer.MAX_VALUE);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
