import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) throws IOException {
    // Определяем сокет сервера
    InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",
            23334);
    final SocketChannel socketChannel = SocketChannel.open();
    // подключаемся к серверу
    socketChannel.connect(socketAddress);

    try (Scanner scanner = new Scanner(System.in)) {
      // Определяем буфер для получения данных
      final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
      String msg;
      while (true) {
        System.out.println("Введите строку...");
        msg = scanner.nextLine();
        if ("end".equals(msg)) break;
        if ("".equals(msg)) continue;
        socketChannel.write(
                ByteBuffer.wrap(
                        msg.getBytes(StandardCharsets.UTF_8)));
        int bytesCount = socketChannel.read(inputBuffer);
        System.out.println("SERVER: " + new String(inputBuffer.array(), 0, bytesCount,
                StandardCharsets.UTF_8).trim());
        inputBuffer.clear();
      }
    } finally {
      socketChannel.close();
    }
  }
}
