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
	 * NioEventLoopGroup实际上是个线程池
	 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件，
	 * 每一个NioEventLoop负责处理m个Channel
	 * NioEventLoopGroup从NioEventLoop数组中挨个取出NioEventLoop来处理Channel
	 */
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	protected static void run() throws Exception {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);
		b.childHandler(new InitChannelEnvirment());
		b.option(ChannelOption.SO_BACKLOG, 1024);  //链接数
		b.option(ChannelOption.TCP_NODELAY, true);
		ChannelFuture f = b.bind(Ip, PORT).sync();
		MyChannelHandlerPool.channelGroup.writeAndFlush("再发送一个试试");
		if(f.isSuccess()) {
			System.out.println("TCP服务器已经启动");
		}
	}
}
