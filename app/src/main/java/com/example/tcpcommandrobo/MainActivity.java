package com.example.tcpcommandrobo;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
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



        TCPClient mTCPClient = new TCPClient();
        connectToServer = (Button)findViewById(R.id.buttonConnect);
        ip = (EditText)findViewById(R.id.ipText);
        port = (EditText)findViewById(R.id.portText);



        connectToServer.setOnClickListener(new View.OnClickListener() {
            Fragment commandFragment = new CommandFragment();
            Bundle args = new Bundle();

            @Override
            public void onClick(View v) {

                //mTCPClient.Connect(ip.getText().toString(),Integer.parseInt(port.getText().toString()));

                args.putString("ip",ip.getText().toString());
                args.putString("port",port.getText().toString());

                commandFragment.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.homepage_fragment,
                        commandFragment).commit();




                //Toast.makeText(getApplicationContext(), ip.getText().toString(),Toast.LENGTH_SHORT).show();
            }

        });







    }
}
