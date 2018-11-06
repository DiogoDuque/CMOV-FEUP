package com.cmov.tp1.terminal;

import android.nfc.NfcAdapter.ReaderCallback;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

public class CardReader implements ReaderCallback {
  private static final String TAG = "CardReader";
  private static final String LOYALTY_CARD_AID = "F222222222";
  private static final String SELECT_APDU_HEADER = "00A40400";
  private static final byte[] SELECT_OK_SW = {(byte)0x90, (byte)0x00};
  private CardReaderCallback accNrCallback;

  public interface CardReaderCallback {
    void onCardNrReceived(String account);
  }

  CardReader(CardReaderCallback callback) {
    accNrCallback = callback;
  }

  @Override
  public void onTagDiscovered(Tag tag) {
    IsoDep isoDep = IsoDep.get(tag);
    if (isoDep != null) {
      try {
        isoDep.connect();
        byte[] command = BuildSelectApdu(LOYALTY_CARD_AID);
        byte[] result = isoDep.transceive(command);
        int rLen = result.length;
        byte[] status = {result[rLen - 2], result[rLen - 1]};
        byte[] payload = Arrays.copyOf(result, rLen - 2);
        if (Arrays.equals(SELECT_OK_SW, status)) {
          String accNumber = new String(payload, "UTF-8");
          accNrCallback.onCardNrReceived(accNumber);
        }
      } catch (IOException e) {
        Log.e(TAG, "Error communicating with card: " + e.toString());
      }
    }
  }

  private byte[] BuildSelectApdu(String aid) {
    return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
  }

  private byte[] HexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
}
