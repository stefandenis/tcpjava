package com.example.tcpcommandrobo;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPClient {

    private static String TAG = "TCPClient"; //For debugging, always a good idea to have defined
    private String severIp =   "192.168.0.61";
    private long startTime = 0l;
    private int serverPort = 6646;
    private Socket connectionSocket;

    private SendRunnable sendRunnable;
    private Thread sendThread;
    private String dataToSend;


    /**
     023
     * Returns true if TCPClient is connected, else false
     024
     * @return Boolean
    025
     */

    public boolean isConnected() {
        return connectionSocket != null && connectionSocket.isConnected() && !connectionSocket.isClosed();
    }


    public void Connect(String ip, int port) {
        severIp = ip;
        serverPort = port;
        new Thread(new ConnectRunnable()).start();


    }

    public void WriteCommand(String cmd) {

        Log.d(TAG, "CONNECTION STATUS :" + isConnected());


        if (isConnected()) {
            startSending();
            sendRunnable.Send(cmd);
        }
    }

    private void startSending() {

        sendRunnable = new SendRunnable(connectionSocket);
        sendThread = new Thread(sendRunnable);
        sendThread.start();

    }


    public class SendRunnable implements Runnable {


        String data;
        private OutputStream out;
        private boolean hasMessage = false;


        public SendRunnable(Socket server) {
            try {
                this.out = server.getOutputStream();
            } catch (IOException e) {
            }
        }

        /**
         * Send data as bytes to the server
         * @param command
         */
        public void Send(String command) {
            this.data = command;

            this.hasMessage = true;
        }




        @Override
        public void run() {

            byte[] b = data.getBytes();

            Log.d(TAG, "Sending started");
            while (!Thread.currentThread().isInterrupted() && isConnected()) {
                if (this.hasMessage) {
                    startTime = System.currentTimeMillis();
                    try {
                        //Send the length of the data to be sent
                        this.out.write(ByteBuffer.allocate(4).putInt(b.length).array());
                        //Send the data
                        this.out.write(b, 0, b.length);
                        //Flush the stream to be sure all bytes has been written out
                        this.out.flush();
                    } catch (IOException e) {

                        Log.d(TAG, "ERROR on sending :"+ e);
                    }

                    this.hasMessage = false;
                    this.data =  null;

                    long time = System.currentTimeMillis() - startTime;

                    Log.d(TAG, "Command has been sent! Current duration: " + time + "ms");
                }
            }
            Log.d(TAG, "Sending stopped");
        }
    }


    public class ConnectRunnable implements Runnable {



        public void run() {

            try {

                Log.d(TAG, "C: Connecting...");

                InetAddress serverAddr = InetAddress.getByName(severIp);

                startTime = System.currentTimeMillis();

                //Create a new instance of Socket

                connectionSocket = new Socket();

                //Start connecting to the server with 5000ms timeout

                //This will block the thread until a connection is established

                connectionSocket.connect(new InetSocketAddress(serverAddr, serverPort), 5000);



                long time = System.currentTimeMillis() - startTime;

                Log.d(TAG, "Connected! Current duration: " + time + "ms");



            } catch (Exception e) {

               //Catch the exception that socket.connect might throw
                Log.d(TAG, "Error: "+ e);

            }

            Log.d(TAG, "Connetion thread stopped");

        }

    }













}
