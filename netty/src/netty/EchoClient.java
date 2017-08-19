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
            b.group(group) // ע���̳߳�
                    .channel(NioSocketChannel.class) // ʹ��NioSocketChannel����Ϊ�����õ�channel��
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // �����Ӷ˿ں�host��Ϣ
                    .handler(new ChannelInitializer<SocketChannel>() { // �����ӳ�ʼ����
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    System.out.println("connected...");
                                    ch.pipeline().addLast(new EchoClientHandler());
                                }
                            });
            System.out.println("created..");

            ChannelFuture cf = b.connect().sync(); // �첽���ӷ�����
            System.out.println("connected..."); // �������
  
            cf.channel().closeFuture().sync(); // �첽�ȴ��ر�����channel
            System.out.println("closed.."); // �ر����
        } finally {
            group.shutdownGracefully().sync(); // �ͷ��̳߳���Դ
            this.setVisible(false);
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 65535).start(); // ����127.0.0.1/65535��������
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