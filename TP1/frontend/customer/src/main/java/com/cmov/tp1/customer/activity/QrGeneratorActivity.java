package com.cmov.tp1.customer.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.TicketTerminal;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

public class QrGeneratorActivity extends AppCompatActivity {
    ImageView qrCodeImageview;
    TextView errorTv;
    public final static int DIMENSION=500;
    private TicketTerminal ticket;
    private String cafeteriaOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        qrCodeImageview = findViewById(R.id.img_qr_code);
        errorTv = findViewById(R.id.tv_error);

        Bundle b = getIntent().getExtras();

        if(b.getString("type").equals("cafeteria")) {
            cafeteriaOrder = b.getString("cafeteriaOrder");
            generate(true);
        }
        else{
            if(b.getIntegerArrayList("ticketsID") != null)
                ticket = new TicketTerminal(b.getInt("userId"), b.getInt("eventId"), b.getIntegerArrayList("ticketsID"), b.getString("name"), b.getString("date"), b.getDouble("price"));

            else
                ticket = new TicketTerminal(b.getInt("userId"), b.getInt("eventId"), b.getInt("id"), b.getString("name"), b.getString("date"), b.getDouble("price"));

            generate(false);
        }
    }

    void generate(boolean type) {
        if(type)
            new Thread(new convertToQR(cafeteriaOrder)).start();
        else
            new Thread(new convertToQR(ticket.getTicket())).start();
    }

    class convertToQR implements Runnable {
        String content;

        convertToQR(String value) {
            content = value;
        }

        @Override
        public void run() {
            final Bitmap bitmap;
            final String errorMsg;
            try {
                bitmap = encodeAsBitmap(content);
                runOnUiThread(new Runnable() {  // runOnUiThread method used to do UI task in main thread.
                    @Override
                    public void run() {
                        qrCodeImageview.setImageBitmap(bitmap);
                    }
                });
            }
            catch (WriterException e) {
                errorMsg = e.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        errorTv.setText(errorMsg);
                    }
                });
            }
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, DIMENSION, DIMENSION, hints);
        }
        catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.orange):getResources().getColor(R.color.background_material_light);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}

