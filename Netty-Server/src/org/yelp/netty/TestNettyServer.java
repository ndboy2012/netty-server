package org.yelp.netty;

public class TestNettyServer {
	public static void main(String[] args) {
		try {
			System.out.println("开始启动TCP服务器");
			TcpServer.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
