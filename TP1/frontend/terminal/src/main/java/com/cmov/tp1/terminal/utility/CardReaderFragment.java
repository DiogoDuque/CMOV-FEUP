package com.cmov.tp1.terminal.utility;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.terminal.R;
import com.cmov.tp1.terminal.activity.ResultActivity;
import com.cmov.tp1.terminal.utility.CardReader;

import org.json.JSONException;
import org.json.JSONObject;


public class CardReaderFragment extends Fragment implements CardReader.CardReaderCallback {
  public static int READER_FLAGS = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
  private TextView showName;
  private TextView showDate;
  public CardReader cardReader;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_card_reader, container, false);
    if (v != null) {
      showName = v.findViewById(R.id.show_name);
      showName.setText("Waiting...");

      showDate = v.findViewById(R.id.show_date);
      showDate.setText("Waiting...");

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

        String[] strings = accNr.split("/");
        Integer userId = Integer.parseInt(strings[0]);
        Integer quantity = Integer.parseInt(strings[1]);
        String show_Date = strings[3];
        String show_name = "";
        Integer showId = 0;
        String[] shows = null;

        if(strings.length == 4)
            showId = Integer.parseInt(strings[2]);
        else
            shows = strings[2].split("-");

        /**if(quantity == 1){
          NetworkRequests.checkTickets(this, showId, show_Date, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
              show_name = json.getString("showName");
              Toast.makeText(activity.getBaseContext(), "Ticket validated successfully", Toast.LENGTH_SHORT).show();
              activity.finish();
            }

            @Override
            public void onError(JSONObject json) {
              Toast.makeText(getBaseContext(), "Error validating ticket", Toast.LENGTH_LONG).show();
            }
          });
        }
        else{
          for(int i = 0; i < shows.length; i++){
            NetworkRequests.checkTickets(this, shows[i], show_Date, new HTTPRequestUtility.OnRequestCompleted() {
              @Override
              public void onSuccess(JSONObject json) {
                show_name = json.getString("showName");
              }

              @Override
              public void onError(JSONObject json) {
                Toast.makeText(getBaseContext(), "Error validating ticket", Toast.LENGTH_LONG).show();
              }
            });
          }

        }**/

        Intent intent = new Intent(getActivity(), ResultActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("result", true);

        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
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
