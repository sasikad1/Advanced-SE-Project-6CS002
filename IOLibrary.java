package base;
import java.io.*;
import java.net.*;
/**
 * @author Kevan Buckley, maintained by __student
 * @version 2.0, 2014
 */

public final class IOLibrary {
   public static String readInputLine() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    do {
      try {
        return reader.readLine();
      } catch (Exception e) {
      }
    } while (true);
  }

  public static InetAddress readIPAddress() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    do {
      try {
        String[] ipParts = reader.readLine().split("\\.");
        byte[] ipBytes = { Byte.parseByte(ipParts[0]),Byte.parseByte(ipParts[1]),Byte.parseByte(ipParts[2]),Byte.parseByte(ipParts[3])};
        return Inet4Address.getByAddress(ipBytes);
      } catch (Exception e) {
      }
    } while (true);
  }

}