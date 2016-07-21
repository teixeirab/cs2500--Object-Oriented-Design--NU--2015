package Jesse_model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client for the remote reference implementation of the game. This is
 * basically just a TCP client with a hard-coded host and port to connect to.
 */
public class NetworkClientMain {
  public static final String HOST = "cs3500.ccs.neu.edu";
  public static final int PORT = 3500;

  public static void main(String[] args) {
    try (Socket socket = new Socket(HOST, PORT);
         InputStream in = socket.getInputStream();
         OutputStream out = socket.getOutputStream())
    {
      Thread sending   = new StreamTransferer(System.in, out);
      Thread receiving = new StreamTransferer(in, System.out);

      sending.start();
      receiving.start();

      sending.join();
      socket.shutdownOutput();

      receiving.join();
    } catch (Exception e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

  private static final int BUF_SIZE = 1024;

  static class StreamTransferer extends Thread {
    final byte buf[] = new byte[BUF_SIZE];
    final InputStream in;
    final OutputStream out;

    StreamTransferer(InputStream in, OutputStream out) {
      this.in = in;
      this.out = out;
    }

    @Override
    public void run() {
      int count;

      try {
        while ((count = in.read(buf)) > 0) {
          out.write(buf, 0, count);
        }
      } catch (IOException e) {
        e.printStackTrace(System.err);
        System.exit(1);
      }
    }
  }
}
