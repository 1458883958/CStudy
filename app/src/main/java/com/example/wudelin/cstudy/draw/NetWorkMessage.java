package com.example.wudelin.cstudy.draw;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetWorkMessage {

    private Socket scoket=new Socket();
    private static NetWorkMessage _instaance = new NetWorkMessage();
    public boolean isDraw;
    private YouDrawIGuessActivity activity;

    public YouDrawIGuessActivity getActivity() {
        return activity;
    }

    public void setActivity(YouDrawIGuessActivity activity) {
        this.activity = activity;
    }



    public static NetWorkMessage get_instaance() {
        return _instaance;
    }


    public void sendDrawMsgToServer(DrawStep drawStep) {
        try {

            DataOutputStream outputStream = new DataOutputStream(scoket.getOutputStream());
            int len = 10;
            ByteArray data = new ByteArray();
            data.writeInt(len);
            data.writeByte(Command.DRAW);
            data.writeByte(drawStep.getType());
            data.writeInt(drawStep.getxP());
            data.writeInt(drawStep.getyP());
            outputStream.write(data.toByteArray());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void createNetWork() {
        new Thread(createnetwork).start();
    }

    public void receiveDrawNetWork() {
        new Thread(receiveDrawnetwork).start();
    }

    public void closeNetWork() {
        new Thread(closenetwork).start();
    }

    private Runnable createnetwork = new Runnable() {
        @Override
        public void run() {
            try {
                scoket = new Socket("114.67.224.207", 8889);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable closenetwork = new Runnable() {
        @Override
        public void run() {
           if(scoket.isConnected()) {
               try {
                   scoket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    };



    private Runnable receiveDrawnetwork = new Runnable() {
        @Override
        public void run() {
            while (true) {
                receiveDraw();
            }
        }
    };

    private void receiveDraw() {

        try {
            DataInputStream inputStream = new DataInputStream(scoket.getInputStream());
            scoket.setSoTimeout(10000);
            if (inputStream.available() != 0) {
                int len = inputStream.readInt();
                byte[] buffer = new byte[len];
                inputStream.read(buffer, 0, len);
                ByteArray data = new ByteArray(buffer);
                byte cmd = data.readByte();
                if (cmd == Command.LOGIN) {
                    int tag = data.readInt();
                    if (tag == 1) {
                        setDrawer(true);
                    } else {
                        setDrawer(true);
                    }
                } else if (cmd == Command.DRAW) {
                    byte type = data.readByte();
                    int x = data.readInt();
                    int y = data.readInt();

                    DrawStep drawStep = new DrawStep();
                    drawStep.setxP(x);
                    drawStep.setyP(y);
                    drawStep.setType(type);

                    activity.addOneStep(drawStep);
                }
                //YouDrawIGuessActivity.drawStep(msg);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public boolean isDrawer() {
        return isDraw;
    }

    public void setDrawer(boolean draw) {
        isDraw = draw;
    }


}
