package org.yelp.netty;

import java.util.Date;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TcpServerHandler extends ChannelHandlerAdapter {  
	
	/**
	 * channelAction
	 * channel ͨ��
	 * action ��Ծ��
	 * ���ͻ����������ӷ������˵����Ӻ����ͨ�����ǻ�Ծ��
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().localAddress().toString()+"channelActive");
		MyChannelHandlerPool.channelGroup.add(ctx.channel());
		//֪ͨ���Ѿ������Ͽͻ���
		String str = "���Ѿ���������������"+" "+ctx.channel().id()+new Date()+" "+ctx.channel().localAddress();
		ctx.writeAndFlush(str);
	}
	
	/*
	* channelInactive
	* channel ͨ��
	* Inactive ����Ծ��
	* ���ͻ��������Ͽ�����˵����Ӻ����ͨ�����ǲ���Ծ�ġ�Ҳ����˵�ͻ��������˵Ĺر���ͨ��ͨ�����Ҳ����Դ�������
	*/
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	// ��channelGroup���Ƴ������пͻ����˳����Ƴ�channel��
	MyChannelHandlerPool.channelGroup.remove(ctx.channel());
	System.out.println(ctx.channel().localAddress().toString()+" channelInactive");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
        System.out.println("��������ȡ���ͻ�������:"+msg);
        MyChannelHandlerPool.channelGroup.writeAndFlush(msg);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("������readComplete ��Ӧ���");
    }
     
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("�������쳣�˳�"+cause.getMessage());
    }
	
}  
