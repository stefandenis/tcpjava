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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CommandFragment extends Fragment {
    Button forwardButton,stopButton,backwardButton,leftButton,rightButton;
    TextView statusConnection;
    private String ip;
    private String port;
    private SeekBar seekBar;
    private TextView pwmDuty;

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
        seekBar = (SeekBar) view.findViewById(R.id.controlSpeed);
        pwmDuty = (TextView) view.findViewById(R.id.pwmDuty);

        pwmDuty.setText("PWM DUTY: "+ seekBar.getProgress() + "/" + seekBar.getMax());


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

                mTCPClient.WriteCommand("backwardx");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("stopx");
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("leftx");
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTCPClient.WriteCommand("rightx");
            }
        });






        seekBar.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                    int seekbar_progress;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        seekbar_progress = progress;
                        pwmDuty.setText("PWM DUTY: "+ progress + "/" + seekBar.getMax());
                       // Toast.makeText(getActivity(),"Seekbar in progress", Toast.LENGTH_SHORT).show();
                        mTCPClient.WriteCommand(Integer.toString(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                       // Toast.makeText(getActivity(),"Seekbar start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pwmDuty.setText("PWM DUTY: "+ seekbar_progress + "/" + seekBar.getMax());
                        //Toast.makeText(getActivity(),"Seekbar stop", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        return view;

    }




}
