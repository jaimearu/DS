import java.io.*;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;

public class UDPServer {
    private DatagramSocket socket;
    byte[] buffer;

    public UDPServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Syntax: <file> <port>");
            return;
        }

        String path = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            UDPServer server = new UDPServer(port);
            server.loadFile(path);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1000], 1);
            socket.receive(request);

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
        }
    }

    private void loadFile(String pad) throws IOException {
        File temp = new File(pad);
        buffer = Files.readAllBytes(temp.toPath());
    }
}