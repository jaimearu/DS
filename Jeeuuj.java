import java.net.SocketException;

public class Jeeuuj {
    public static void main(String[] args) throws SocketException {
        UDPServer.main(new String[] {"src/Quotes.txt", "17"});
    }
}
