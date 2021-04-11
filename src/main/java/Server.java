import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {
    // Занимаем порт, определяя серверный сокет
    ServerSocket servSocket = new ServerSocket(23444);
    while (true) {
      // Ждем подключения клиента и получаем потоки для дальнейшей работы
      try (Socket socket = servSocket.accept();
           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
           BufferedReader in = new BufferedReader(new
                   InputStreamReader(socket.getInputStream()))) {
        String line;
        while ((line = in.readLine()) != null) {
          // Выход если от клиента получили end
          if (line.equals("end")) {
            break;
          } else {
            // Пишем ответ
            out.println(extraTrim(line));
          }
        }
      } catch (IOException ex) {
        ex.printStackTrace(System.out);
      }
    }
  }

  // возвращает строку без лишних пробелов
  public static String extraTrim(String input) {
    return input.trim()
            .replaceAll("\\s+", " ")
            .replaceAll("\\s(?=\\p{Punct})", "");
  }
}

