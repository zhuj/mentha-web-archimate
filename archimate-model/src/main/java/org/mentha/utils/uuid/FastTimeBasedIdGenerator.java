package org.mentha.utils.uuid;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FastTimeBasedIdGenerator {

  private final Object lock = new Object();
  private long sequenceNumber;
  private long lastTimestamp;

  private static final FastTimeBasedIdGenerator INSTANCE = new FastTimeBasedIdGenerator();
  public static String generateId(short typeIdentifier, long currentTimeMillis) {
    return INSTANCE.generateIdFromTimestamp(typeIdentifier, currentTimeMillis);
  }

  public static boolean validateId(short typeIdentifier, String id) {
    return (getCheckSum(typeIdentifier) == getCheckSum(id));
  }

  public static int getCheckSum(short typeIdentifier) {
    return typeIdentifier & 0x7ff;
  }

  public static int getCheckSum(String id) {
    return ( (index(id.charAt(2)) << 6) + index(id.charAt(3)) ) & 0x7ff;
  }

  // 64 digits = 6 bits
  private final static byte[] digits = {
          '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' ,
          '8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f' ,
          'g' , 'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
          'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' ,
          'w' , 'x' , 'y' , 'z' , 'A' , 'B' , 'C' , 'D' ,
          'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' ,
          'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' ,
          'U' , 'V' , 'W' , 'X' , 'Y' , 'Z' , '$' , '#'
  };

  private static final int index(char c) {
    byte index = indexes[c];
    if (index < 0) { throw new IllegalStateException(); }
    return index;
  }

  private static final byte[] indexes = new byte[128];
  static {
    Arrays.fill(indexes, (byte)-1);
    for (byte i=0; i<digits.length; i++) {
      indexes[digits[i]] = i;
    }
  }

  @SuppressWarnings("UnusedReturnValue")
  private static int formatUnsignedLong(long val, int shift, byte[] buf, int offset, int len) {
    int charPos = len;
    int radix = 1 << shift;
    int mask = radix - 1;
    do {
      buf[offset + --charPos] = digits[((int) val) & mask];
      val >>>= shift;
    } while (charPos > 0);

    return charPos;
  }

  /**
   * Combine bits to make 72bit identifier string representation
   * @param typeIdentifier (last 11 bits are only used)
   * @param timestamp (last 44 bits are only used)
   * @param seq (last 16 bits are only used)
   * @return 72bit (12 characters) identifier string representation
   */
  public static String generateId(short typeIdentifier, long timestamp, long seq) {
    // we have to preserve the last 44 bits of timestamp (it will work up to Mon Jun 23 2527 06:20:44)
    timestamp &= 0xfffffffffffL; // now there is 44 buts of data

    long id11 = (typeIdentifier & 0x7ff); // 11 bits of data

    long tl08 = (timestamp & 0xff); // 8 bits of data
    long th36 = (timestamp >>> 8); // 36 bits of data

    seq &= 0xffff; // 16 bits of data
    long sl04 = (seq & 0xf); // 4 bits of data
    long sh12 = (seq >>> 4); // 12 bits of data

    long a24 = (((tl08 << 4) | sl04) << 11) | id11; // 1 + 8 + 4 + 11 = 24 bits (the first bit is always zero)
    long b48 = ((th36 << 8) | sh12); // 36 + 12 = 48 bits

    byte[] buff = new byte[12];
    formatUnsignedLong(a24, 6, buff, 0, 4); // (24/6) = 4 digits
    formatUnsignedLong(b48, 6, buff, 4, 8); // (48/6) = 8 digits

    return new String(buff, StandardCharsets.US_ASCII);
  }

  private String generateIdFromTimestamp(short typeIdentifier, long currentTimeMillis) {
    long seq = nextSeq(currentTimeMillis);
    return generateId(typeIdentifier, currentTimeMillis, seq);
  }

  private long nextSeq(long currentTimeMillis) {
    synchronized (lock) {
      if (currentTimeMillis > lastTimestamp) {
        lastTimestamp = currentTimeMillis;
        return (sequenceNumber = 0);
      } else {
        return (++sequenceNumber);
      }
    }
  }

}