package com.zkkj.backend.common.websocket;

/**
 * Created by Todd on 2017/3/22.
 */

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import com.zkkj.backend.common.util.GsonUtil;



public class OkHttpWebsocketUtil {
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private static OkHttpClient sClient;
    private static WebSocket sWebSocket;

    public static synchronized void startRequest() {
        if (sClient == null) {
            sClient = new OkHttpClient();
        }
        if (sWebSocket == null) {
            Request request = new Request.Builder().url("ws://192.168.3.106:8080/zkkjweb/test?ip=192.168.3.106&deviceId=AEBEFDAGEAC").build();

            EchoWebSocketListener listener = new EchoWebSocketListener();
            sWebSocket = sClient.newWebSocket(request, listener);
        }
    }

    private static void sendMessage(WebSocket webSocket,String message) {
        webSocket.send(message);
       // webSocket.send(ByteString.decodeHex("deadbeef"));
    }

    public static void sendMessage() {
        WebSocket webSocket;
        synchronized (OkHttpWebsocketUtil.class) {
            webSocket = sWebSocket;
        }
        if (webSocket != null) {
        	Map<String,String> message = new HashMap<String,String>(2);
        	Map<String,String> data = new HashMap<String,String>(1);
        	data.put("data", "Hello World");
        	message.put("method", "System_operation");
        	message.put("message", GsonUtil.getGson().toJson(data));
            sendMessage(webSocket,GsonUtil.getGson().toJson(message));
        }
    }

    public static synchronized void closeWebSocket() {
        if (sWebSocket != null) {
            sWebSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
            sWebSocket = null;
        }
    }

    public static synchronized void destroy() {
        if (sClient != null) {
            sClient.dispatcher().executorService().shutdown();
            sClient = null;
        }
    }

    private static void resetWebSocket() {
        synchronized(OkHttpWebsocketUtil.class) {
            sWebSocket = null;
        }
    }

    public static class EchoWebSocketListener extends WebSocketListener {
        private static final String TAG = "EchoWebSocketListener";

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
        
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
        	System.out.println("Receiving: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
        	System.out.println ("Receiving: " + bytes.hex());
        }

        @Override

        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            System.out.println ("Closing: " + code + " " + reason);
            resetWebSocket();
        }

        @Override

       public void onClosed(WebSocket webSocket, int code, String reason) {
        	System.out.println ("Closed: " + code + " " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        	System.out.println ("onFailure have a Exception! ");
        	//  sendMessage(webSocket,"hehe");
            t.printStackTrace();
            resetWebSocket();
        }
    }
    
    public static void main(String[] args){
    	
    	OkHttpWebsocketUtil.startRequest();
    	OkHttpWebsocketUtil.sendMessage();
    }
}