package org.yelp.netty;

import java.util.Date;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TcpServerHandler extends ChannelHandlerAdapter {  
	
	/**
	 * channelAction
	 * channel 通道
	 * action 活跃的
	 * 当客户端主动链接服务器端的连接后，这个通道就是活跃的
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().localAddress().toString()+"channelActive");
		MyChannelHandlerPool.channelGroup.add(ctx.channel());
		//通知您已经链接上客户端
		String str = "您已经开启与服务端链接"+" "+ctx.channel().id()+new Date()+" "+ctx.channel().localAddress();
		ctx.writeAndFlush(str);
	}
	
	/*
	* channelInactive
	* channel 通道
	* Inactive 不活跃的
	* 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
	*/
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	// 从channelGroup中移除，当有客户端退出后，移除channel。
	MyChannelHandlerPool.channelGroup.remove(ctx.channel());
	System.out.println(ctx.channel().localAddress().toString()+" channelInactive");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
        System.out.println("服务器读取到客户端请求:"+msg);
        MyChannelHandlerPool.channelGroup.writeAndFlush(msg);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("服务器readComplete 响应完成");
    }
     
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("服务器异常退出"+cause.getMessage());
    }
	
}  
