package org.yelp.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TcpServer {

	private static final String Ip = "127.0.0.1";
	private static final int PORT = 9999;
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
	protected static final int BIZTHREADSIZE = 4;

	/**
	 * NioEventLoopGroupʵ�����Ǹ��̳߳�
	 * NioEventLoopGroup�ں�̨������n��NioEventLoop������Channel�¼���
	 * ÿһ��NioEventLoop������m��Channel
	 * NioEventLoopGroup��NioEventLoop�����а���ȡ��NioEventLoop������Channel
	 */
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	protected static void run() throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);
		b.childHandler(new InitChannelEnvirment());
		b.option(ChannelOption.SO_BACKLOG, 1024);  //������
		b.option(ChannelOption.TCP_NODELAY, true);
		ChannelFuture f = b.bind(Ip, PORT).sync();
		MyChannelHandlerPool.channelGroup.writeAndFlush("�ٷ���һ������");
		if(f.isSuccess()) {
			System.out.println("TCP�������Ѿ�����");
		}
	}
}
