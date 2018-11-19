package com.cmov.tp1.cafeteria.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.cafeteria.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();
        TextView text = (TextView)findViewById(R.id.result);
        ImageView image = (ImageView)findViewById(R.id.imageView);

        if(b.getBoolean("result")){
            JSONObject json;
            double cost = 0;
            try {
                json = new JSONObject(b.getString("message"));
                cost = json.optDouble("cost");
                if(cost == Double.NaN) {
                    cost = json.getInt("cost");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            text.setText("Cafeteria order was validated with success");
            text.setTextColor(getResources().getColor(R.color.green));
            image.setImageResource(R.drawable.right);
            showDialog(this, "Cost Alert","Final cost was "+cost+" and is being credited to the credit card.", "OK").show();
        }
        else{
            text.setText("The cafeteria order is not valid");
            text.setTextColor(getResources().getColor(R.color.red));
            image.setImageResource(R.drawable.wrong);
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(act, MainActivity.class);
                act.startActivity(intent);
            }
        });
        return downloadDialog.show();
    }
}
