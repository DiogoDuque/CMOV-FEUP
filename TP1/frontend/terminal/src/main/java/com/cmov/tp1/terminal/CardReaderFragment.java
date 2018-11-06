package com.cmov.tp1.terminal;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CardReaderFragment extends Fragment implements CardReader.CardReaderCallback {
  public static int READER_FLAGS = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
  private TextView tv_accNr;
  public CardReader cardReader;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_card_reader, container, false);
    if (v != null) {
      /**tv_accNr = v.findViewById(R.id.card_account_field);
      tv_accNr.setText(R.string.tv_accnr);**/

      cardReader = new CardReader(this);
      enableReaderMode();
    }
    return v;
  }

  @Override
  public void onCardNrReceived(final String accNr) {
    FragmentActivity act = getActivity();
    if (act != null)
      act.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        tv_accNr.setText(accNr);
      }
    });
  }

  @Override
  public void onPause() {
    super.onPause();
    disableReaderMode();
  }

  @Override
  public void onResume() {
    super.onResume();
    enableReaderMode();
  }

  private void enableReaderMode() {
    Activity activity = getActivity();
    NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
    if (nfc != null) {
      nfc.enableReaderMode(activity, cardReader, READER_FLAGS, null);
    }
  }

  private void disableReaderMode() {
    Activity activity = getActivity();
    NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
    if (nfc != null) {
      nfc.disableReaderMode(activity);
    }
  }
}
