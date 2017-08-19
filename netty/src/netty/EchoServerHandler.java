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
	//缓存区
	//public static ByteBuf buf=Unpooled.buffer(1024);
	public static String sbuf=new String();
	private static Integer index=1;
	//一个客户端连接上,激活时候,存储客户端ctx
	
	
	
	
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
	
	
	
	
	//读取从客户端传来的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ByteBuf buf=(ByteBuf)msg;
    	byte[] req=new byte[buf.readableBytes()];
    	buf.readBytes(req);
    	sbuf=new String(req,"UTF-8");
 //   	sbuf=(String)msg;
    	System.out.println("server channelRead...;     received:"+sbuf);
    }
    
    
    //一个客户端断开,移除ctx
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	 //System.err.println(ctx.channel().id()+" has exited");
	 //EchoServer.map.remove(ctx.channel().id());
   };
   //读取完成，进行业务处理
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
    		//返回某个客户端传来的数据到所有的客户端控制台显示
    		for(Map.Entry<Integer, ChannelHandlerContext> entry:EchoServer.map.entrySet()){
    			
    			entry.getValue().writeAndFlush(Unpooled.copiedBuffer("i"+sbuf+"*",CharsetUtil.UTF_8));
    			
    		}
    		sbuf="";
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        //ctx.flush(); // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        //ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    //	buf.clear();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server occur exception:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close(); // 关闭发生异常的连接
    }
}