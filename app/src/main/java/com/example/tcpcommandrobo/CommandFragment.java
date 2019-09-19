package com.example.tcpcommandrobo;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CommandFragment extends Fragment {
    Button forwardButton,stopButton,backwardButton,leftButton,rightButton;
    TextView statusConnection;
    private String ip;
    private String port;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.command_fragment, container, false);


        forwardButton = (Button) view.findViewById(R.id.forwardButton);
        stopButton = (Button) view.findViewById(R.id.stopButton);
        leftButton = (Button) view.findViewById(R.id.leftButton);
        rightButton = (Button) view.findViewById(R.id.rightButton);
        backwardButton = (Button) view.findViewById(R.id.backwardButton);
        statusConnection = (TextView) view.findViewById(R.id.statusText);

        Bundle bundle = getArguments();
        if(bundle!=null){
            ip = bundle.getString("ip");
            port = bundle.getString("port");

        }


        TCPClient mTCPClient = new TCPClient();


        mTCPClient.Connect(ip,Integer.parseInt(port));


        while(!mTCPClient.isConnected()){

            ;
        }

        if(mTCPClient.isConnected()){
            statusConnection.setText("STATUS: CONNECTED");
        }





        forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            mTCPClient.WriteCommand("forwardx");
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("backward");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("stop");
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("left");
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("right\n");
            }
        });



        return view;

    }




}
