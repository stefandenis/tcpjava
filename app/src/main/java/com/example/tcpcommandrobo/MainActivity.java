package com.example.tcpcommandrobo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button connectToServer;
    EditText ip,port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);




        connectToServer = (Button)findViewById(R.id.buttonConnect);
        ip = (EditText)findViewById(R.id.ipText);
        port = (EditText)findViewById(R.id.portText);


        connectToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Client client = new Client(ip.getText().toString(),Integer.parseInt(port.getText().toString()));



                Toast.makeText(getApplicationContext(), ip.getText().toString(),Toast.LENGTH_SHORT).show();
            }

        });




    }
}
