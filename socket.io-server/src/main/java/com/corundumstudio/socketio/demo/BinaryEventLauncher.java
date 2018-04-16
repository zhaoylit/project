package com.corundumstudio.socketio.demo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
public class BinaryEventLauncher {
	public static List<SocketIOClient> list = new ArrayList<SocketIOClient>();
	static SocketIOServer server;
	public void startServer() throws InterruptedException{
		Configuration config = new Configuration();
        config.setHostname("192.168.3.4");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);

        server = new SocketIOServer(config);

        server.addEventListener("msg", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
                client.sendEvent("msg", data);
            }
        });
        server.addConnectListener(new ConnectListener() {

			@Override	
			public void onConnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println(client.getRemoteAddress()+"-----------"+"客户端已连接");
				System.out.println("server1" + "************************" + server);
			}
		});
        server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println(client.getRemoteAddress()+"-----------"+"客户端已断开连接");
			}
		});
        server.start();	
        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
	}
	public void sendMessage(){
//		c.sendEvent("msg", "sdfsfsdfsf");
//		System.out.println(list);
		System.out.println("server2" + "************************" + server);
	}
    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
    	//启动服务
//    	new BinaryEventLauncher().startServer();
    	new BinaryEventLauncher().sendMessage();
        
    }
}
