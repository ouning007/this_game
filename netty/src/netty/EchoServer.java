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
	//�˿�
    private final int port;
    
    //��̬map���洢�ͻ������������ctx�������ж�ʶ��ĳ���ͻ��ˣ�
    public static Map<Integer, ChannelHandlerContext> map=new HashMap<Integer,ChannelHandlerContext>();
    //
    public EchoServer(int port) {
        this.port = port;
    } 

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(group) // ���̳߳�
                    .channel(NioServerSocketChannel.class) // ָ��ʹ�õ�channel
                    .localAddress(this.port)// �󶨼����˿�
                    .childHandler(new ChannelInitializer<SocketChannel>() { // �󶨿ͻ�������ʱ�򴥷�����

                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                	ByteBuf delimiter=Unpooled.copiedBuffer("end".getBytes());
                                    System.out.println("connected...; Client:" + ch.remoteAddress());
                                   //ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                                   // ch.pipeline().addLast(new StringDecoder());
                                    ch.pipeline().addLast(new EchoServerHandler()); // �ͻ��˴�������
                                }
                            });
            ChannelFuture cf = sb.bind().sync(); // �������첽������
            System.out.println(EchoServer.class + " started and listen on " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // �رշ�����ͨ��
        } finally {
            group.shutdownGracefully().sync(); // �ͷ��̳߳���Դ
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(65535).start(); // ����
    }
}