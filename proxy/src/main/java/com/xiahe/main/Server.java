package com.xiahe.main;

import com.alibaba.fastjson.JSONObject;
import com.xiahe.processor.Processor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

//服务端
public class Server implements Runnable {
    private Socket socket;

    public Server(Socket socket) {
        super();
        this.socket = socket;
    }

    //请求与响应
    public static String analysis(String input) {
        try {
            JSONObject request = JSONObject.parseObject(input);
            Method method = Processor.class.getMethod(request.getString("request"), JSONObject.class);
            return method.invoke(Processor.class, request).toString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", "failure");
            return jsonObject.toString();
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            for (String input; (input = dataInputStream.readUTF()) != null; ) {
                System.out.println("Input:" + input);
                String analysis = analysis(input);
                dataOutputStream.writeUTF(analysis);
                System.out.println("Output:" + analysis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
