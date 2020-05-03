package com.dengjia.shareusb_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ShareUsb shareUsb;

    private EditText et_send_data;

    private TextView tv_received_data;
    private TextView tv_send_data;

    private Button bt_clear_received_data;
    private Button bt_clear_send_data;
    private Button bt_send_data;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_send_data = findViewById(R.id.et_send_data);
        tv_received_data = findViewById(R.id.tv_received_data);
        tv_send_data = findViewById(R.id.tv_send_data);
        bt_clear_received_data = findViewById(R.id.bt_clear_received_data);
        bt_clear_send_data = findViewById(R.id.bt_clear_send_data);
        bt_send_data = findViewById(R.id.bt_send_data);

        shareUsb = new ShareUsb();
        shareUsb.run(this);
        shareUsb.sendData("Phone's Data");

        shareUsb.addReceiveDataListener(new ShareUsb.ReceiveDataListener() {
            @Override
            public void receiveData(final String data, int dataLength) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_received_data.append("\n" + data);
                    }
                });
            }
        });

        bt_clear_received_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_received_data.setText("");
            }
        });

        bt_clear_send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_send_data.setText("");
            }
        });

        bt_send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUsb.sendData(et_send_data.getText().toString());
                tv_send_data.append("\n" + et_send_data.getText().toString());
            }
        });

    }
}
