package com.xiahe.main;

import com.xiahe.config.Configuration;
import com.xiahe.config.Pool;
import com.xiahe.core.Core;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;

@Component
public class ProxyPool implements Runnable {

    public ProxyPool() {
        Pool.Executors.execute(new Core());
//        Pool.Executors.execute(new ProxyPool());
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Configuration.Port);
            while (true) {
                Pool.Executors.execute(new Server(serverSocket.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
