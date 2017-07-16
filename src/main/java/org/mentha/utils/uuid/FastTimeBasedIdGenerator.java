package org.mentha.utils.uuid;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;

public class FastTimeBasedIdGenerator {

  private final Object lock = new Object();
  private long sequenceNumber;
  private long lastTimestamp;

  private static final long hostIdentifier = getHostId();

  private static final FastTimeBasedIdGenerator INSTANCE = new FastTimeBasedIdGenerator();
  public static String generateId(long typeIdentifier) {
    return INSTANCE.generateIdFromTimestamp(typeIdentifier, System.currentTimeMillis());
  }

  final static char[] digits = {
          '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' ,
          '8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f' ,
          'g' , 'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
          'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' ,
          'w' , 'x' , 'y' , 'z' , 'A' , 'B' , 'C' , 'D' ,
          'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' ,
          'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' ,
          'U' , 'V' , 'W' , 'X' , 'Y' , 'Z' , '$' , '#'
  };

  private static int formatUnsignedLong(long val, int shift, char[] buf, int offset, int len) {
    int charPos = len;
    int radix = 1 << shift;
    int mask = radix - 1;
    do {
      buf[offset + --charPos] = digits[((int) val) & mask];
      val >>>= shift;
    } while (charPos > 0);

    return charPos;
  }

  private String toString(long msb, long lsb) {
    char[] buff = new char[22];
    formatUnsignedLong(msb, 6, buff, 0, 11);
    formatUnsignedLong(lsb, 6, buff, 11, 11);
    return new String(buff);
  }

  private String generateIdFromTimestamp(long typeIdentifier, long currentTimeMillis) {

    synchronized (lock) {
      if (currentTimeMillis > lastTimestamp) {
        lastTimestamp = currentTimeMillis;
        sequenceNumber = 0;
      } else {
        ++sequenceNumber;
      }
    }

    long msb = (currentTimeMillis << 16) | (typeIdentifier & 0xffff);
    long lsb = (hostIdentifier << 16) | (sequenceNumber);
    return toString(msb, lsb);
  }

  private static long getHostId() {
    byte[] mac = null;

    try {
      InetAddress address = InetAddress.getLocalHost();
      NetworkInterface ni = NetworkInterface.getByInetAddress(address);
      if (ni != null) {
        mac = ni.getHardwareAddress();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (mac == null) {
      mac = new byte[6];
      new Random(0).nextBytes(mac);
    }

    // Converts array of unsigned bytes to an long
    long macAddressAsLong = 0;
    for (byte aMac : mac) {
      macAddressAsLong <<= 8;
      macAddressAsLong |= (long) aMac & 0xFF;
    }

    return macAddressAsLong;
  }

}