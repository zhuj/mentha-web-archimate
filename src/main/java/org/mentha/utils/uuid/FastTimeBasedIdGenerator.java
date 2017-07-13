package org.mentha.utils.uuid;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;
import java.util.UUID;

public class FastTimeBasedIdGenerator {

  private final Object lock = new Object();
  private long sequenceNumber;
  private long lastTimestamp;

  private static final long hostIdentifier = getHostId();

  private static final FastTimeBasedIdGenerator INSTANCE = new FastTimeBasedIdGenerator();
  public static String generateId(long typeIdentifier) {
    return INSTANCE.generateIdFromTimestamp(typeIdentifier, System.currentTimeMillis());
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
    return Long.toUnsignedString(msb,32) + ":" + Long.toUnsignedString(lsb, 32);
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

    Random random = new Random(0);
    if (mac == null) {
      mac = new byte[6];
      random.nextBytes(mac);
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