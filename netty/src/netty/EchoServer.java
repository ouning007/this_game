package netty;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextArea;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoServer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//端口
    private final int port;
    
    //静态map，存储客户端连接来后的ctx（便于判断识别某个客户端）
    public static Map<Integer, ChannelHandlerContext> map=new HashMap<Integer,ChannelHandlerContext>();
    //
    public EchoServer(int port) {
        this.port = port;
    } 

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(group) // 绑定线程池
                    .channel(NioServerSocketChannel.class) // 指定使用的channel
                    .localAddress(this.port)// 绑定监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作

                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                	ByteBuf delimiter=Unpooled.copiedBuffer("end".getBytes());
                                    System.out.println("connected...; Client:" + ch.remoteAddress());
                                   //ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                                   // ch.pipeline().addLast(new StringDecoder());
                                    ch.pipeline().addLast(new EchoServerHandler()); // 客户端触发操作
                                }
                            });
            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
            System.out.println(EchoServer.class + " started and listen on " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(65535).start(); // 启动
    }
}