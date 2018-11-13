package com.cmov.tp1.terminal.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cmov.tp1.terminal.R;
import com.cmov.tp1.terminal.networking.HTTPRequestUtility;
import com.cmov.tp1.terminal.networking.NetworkRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView card1 = findViewById(R.id.card_view);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(true);
            }
        });
    }

    public void scan(boolean qrcode) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", qrcode ? "QR_CODE_MODE" : "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        }
        catch (ActivityNotFoundException anfe) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                act.startActivity(intent);
            }
        });
        downloadDialog.setNegativeButton(buttonNo, null);
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d("Validate", contents);
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                getResultScan(contents);
            }
        }
    }

    public void getResultScan(String message){
        String[] strings = message.split("-");
        for(String s: strings) {
            Log.d("Validate", "splitted: "+s);
        }
        Integer userId = Integer.parseInt(strings[0]);
        Integer quantity = Integer.parseInt(strings[1]);
        String show_Date = strings[3];
        final String[] show_name = {""};
        Integer showId = 0;
        String[] shows = null;

        if(strings.length == 4)
            showId = Integer.parseInt(strings[2]);
        else
            shows = strings[2].split("\\+");
        Log.d("Validate","1st show: "+shows[0]);

        if(quantity == 1){
            NetworkRequests.checkTickets(this, showId, show_Date, new HTTPRequestUtility.OnRequestCompleted() {
                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    show_name[0] = json.getString("showName");
                    Toast.makeText(getBaseContext(), "Ticket validated successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(JSONObject json) {
                    Toast.makeText(getBaseContext(), "Error validating ticket", Toast.LENGTH_LONG).show();
                }
            }); }
        else{
            for(int i = 0; i < shows.length; i++){
                NetworkRequests.checkTickets(this, Integer.parseInt(shows[i]), show_Date, new HTTPRequestUtility.OnRequestCompleted() {
                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        show_name[0] = json.getString("showName");
                    }

                    @Override
                    public void onError(JSONObject json) {
                        Toast.makeText(getBaseContext(), "Error validating ticket", Toast.LENGTH_LONG).show();
                    }
                });
            }

        }

        Intent intent = new Intent(this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("result", true);

        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
