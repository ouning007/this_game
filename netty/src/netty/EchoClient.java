package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetSocketAddress;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class EchoClient extends JFrame implements KeyListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String host;
    private final int port;
    private JTextArea textArea=new JTextArea();
    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
        
        //this.setTitle(Random);
        this.setSize(200+(int)(Math.random()*100), 200+(int)(Math.random()*100));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(textArea);
        this.setVisible(true);
        textArea.addKeyListener(this);
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    System.out.println("connected...");
                                    ch.pipeline().addLast(new EchoClientHandler());
                                }
                            });
            System.out.println("created..");

            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            System.out.println("connected..."); // 连接完成
  
            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            System.out.println("closed.."); // 关闭完成
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
            this.setVisible(false);
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 65535).start(); // 连接127.0.0.1/65535，并启动
    }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			String string=textArea.getText();
			//System.out.println(string);
		    
			EchoClientHandler.transport_to_server(string);
			textArea.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}