package org.yelp.netty;

public class TestNettyServer {
	public static void main(String[] args) {
		try {
			System.out.println("��ʼ����TCP������");
			TcpServer.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
