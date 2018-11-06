package com.cmov.tp1.customer.utility;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

import com.cmov.tp1.customer.core.Show;

import java.util.Arrays;

public class CardService extends HostApduService {
  private static final String LOYALTY_CARD_AID = "F222222222";  // AID for this applet service.
  private static final String SELECT_APDU_HEADER = "00A40400";  // SmartCard select AID command
  private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");  // "OK" status word (0x9000)
  private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");  // "UNKNOWN" status word (0X0000)
  private static final byte[] SELECT_APDU = BuildSelectApdu(LOYALTY_CARD_AID);

  @Override
  public byte[] processCommandApdu(byte[] command, Bundle bundle) {  // return card account on Select command (with correct AID)
    if (Arrays.equals(SELECT_APDU, command)) {
      String showInfo = "userID" + "/" + "numberTickets" + "/" + "showID" + "/" + "showDate";
      byte[] accountBytes = showInfo.getBytes();
      return ConcatArrays(accountBytes, SELECT_OK_SW);
    }
    else
      return UNKNOWN_CMD_SW;
  }

  @Override
  public void onDeactivated(int i) {
  }

  public static byte[] BuildSelectApdu(String aid) {
    // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
    return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
  }

  public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
    int len = s.length();
    if (len % 2 == 1)
      throw new IllegalArgumentException("Hex string must have even number of characters");
    byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
    for (int i = 0; i < len; i += 2) // Convert each character into a integer (base-16), then bit-shift into place
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
    return data;
  }

  public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
    int tLen = first.length;
    for (byte[] array : rest)
      tLen += array.length;
    byte[] result = Arrays.copyOf(first, tLen);
    int offset = first.length;
    for (byte[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }
    return result;
  }
}
