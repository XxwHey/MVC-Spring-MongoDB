import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by xiexw on 2017/8/25.
 * java NIO
 */
public class NIOServer {
    private Selector selector;

    public void initServer(int port) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        System.out.println("server start...");

        while (true) {
            selector.select();
            Iterator iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = (SelectionKey) iter.next();
                //已取出的去掉
                iter.remove();
                //客户端连接
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socket = server.accept();
                    socket.configureBlocking(false);
                    socket.write(ByteBuffer.wrap("来自客户端的信息".getBytes()));
                    socket.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socket = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    socket.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data).trim();
                    System.out.println("收到信息：" + message);
                    ByteBuffer wrap = ByteBuffer.wrap(message.getBytes());
                    socket.write(wrap);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }
}
