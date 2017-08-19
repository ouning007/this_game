package netty;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	//������
	//public static ByteBuf buf=Unpooled.buffer(1024);
	public static String sbuf=new String();
	private static Integer index=1;
	//һ���ͻ���������,����ʱ��,�洢�ͻ���ctx
	
	
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(index+" client has connected");
		EchoServer.map.put(index, ctx);
		
		ctx.writeAndFlush(Unpooled.copiedBuffer("p"+index+"*",CharsetUtil.UTF_8));
		
		if(index==2){
		   for(Map.Entry<Integer, ChannelHandlerContext> entry:EchoServer.map.entrySet())
		       entry.getValue().writeAndFlush(Unpooled.copiedBuffer("start*", CharsetUtil.UTF_8));
		}
		
		index++;
		
	};
	
	
	
	
	//��ȡ�ӿͻ��˴���������
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ByteBuf buf=(ByteBuf)msg;
    	byte[] req=new byte[buf.readableBytes()];
    	buf.readBytes(req);
    	sbuf=new String(req,"UTF-8");
 //   	sbuf=(String)msg;
    	System.out.println("server channelRead...;     received:"+sbuf);
    }
    
    
    //һ���ͻ��˶Ͽ�,�Ƴ�ctx
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	 //System.err.println(ctx.channel().id()+" has exited");
	 //EchoServer.map.remove(ctx.channel().id());
   };
   //��ȡ��ɣ�����ҵ����
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception { 
    	System.out.println("server channelReadComplete.."+sbuf);
    	//
    	
//    	if(sbuf.equals("exit")){
//    		
//    	}
    	
    	
    	

    	
if(sbuf.equals("request")){
    		float speed=(float) Math.random()*7+3;
    		System.out.println(speed);
    		for(Map.Entry<Integer, ChannelHandlerContext> entry:EchoServer.map.entrySet()){
    			entry.getValue().writeAndFlush(Unpooled.copiedBuffer("s"+speed+"*",CharsetUtil.UTF_8));  			
    		}
    		sbuf="";
    	}else if(sbuf.equals("score")){
    		for(Map.Entry<Integer, ChannelHandlerContext> entry:EchoServer.map.entrySet()){
    			entry.getValue().writeAndFlush(Unpooled.copiedBuffer("score"+"*",CharsetUtil.UTF_8));  			
    		}
    		sbuf="";
    	}
    	
    	else{
    		//����ĳ���ͻ��˴��������ݵ����еĿͻ��˿���̨��ʾ
    		for(Map.Entry<Integer, ChannelHandlerContext> entry:EchoServer.map.entrySet()){
    			
    			entry.getValue().writeAndFlush(Unpooled.copiedBuffer("i"+sbuf+"*",CharsetUtil.UTF_8));
    			
    		}
    		sbuf="";
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        // ��һ�ַ�����дһ���յ�buf����ˢ��д��������ɺ�ر�sock channel���ӡ�
        // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        //ctx.flush(); // �ڶ��ַ�������client�˹ر�channel���ӣ������Ļ����ᴥ������channelReadComplete������
        //ctx.flush().close().sync(); // �����֣��ĳ�����д��Ҳ���ԣ���������д����û�е�һ�ַ����ĺá�
    //	buf.clear();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server occur exception:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close(); // �رշ����쳣������
    }
}