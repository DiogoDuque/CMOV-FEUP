package com.cmov.tp1.cafeteria.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmov.tp1.cafeteria.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();
        TextView text = (TextView)findViewById(R.id.result);
        ImageView image = (ImageView)findViewById(R.id.imageView);

        if(b.getBoolean("result")){
            text.setText("The cafeteria order was validated with success");
            text.setTextColor(getResources().getColor(R.color.green));
            image.setImageResource(R.drawable.right);
        }
        else{
            text.setText("The cafeteria order is not valid");
            text.setTextColor(getResources().getColor(R.color.red));
            image.setImageResource(R.drawable.wrong);
        }
    }
}
