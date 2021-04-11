import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) throws IOException {
    // Определяем сокет сервера
    Socket socket = new Socket("127.0.0.1", 23444);
    // Получаем входящий и исходящий потоки информации
    try (BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
         PrintWriter out = new PrintWriter(
                 new OutputStreamWriter(socket.getOutputStream()), true);
         Scanner scanner = new Scanner(System.in)) {

      String msg;
      while (true) {
        System.out.println("Введите строку...");
        msg = scanner.nextLine();
        out.println(msg);
        if ("end".equals(msg)) break;

        System.out.println("SERVER: " + in.readLine());
      }
    }
  }
}
