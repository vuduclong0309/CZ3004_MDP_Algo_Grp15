package grp15.rpi;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Comms {
    public static final String START_EXPLORE = "START_EXPLORE";                 // Android --> PC
    public static final String START_FASTEST_PATH = "START_FASTEST_PATH";       // Android --> PC
    public static final String MAP_STRINGS = "MAP";                             // PC --> Android
    public static final String FINAL_MAP = "FINAL";
    public static final String ROBOT_POS = "ROBOT_POS";                             // PC --> Android
    public static final String REQUEST_SENSE = "o";                         // PC --> Arduino
    public static final String INSTRUCTIONS = "INSTR";                          // PC --> Arduino
    public static final String SENSOR_DATA = "SDATA";                           // Arduino --> PC

    private static Comms comm = null;
    private static Socket conn = null;

    private BufferedWriter writer;
    private BufferedReader reader;


    public static Comms getComm() {
        return new Comms();
    }

    public void openConnection() {
        System.out.println("Opening connection...");

        try {
            String HOST = "192.168.15.1";
            int PORT = 4957;
            conn = new Socket(HOST, PORT);

            writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(conn.getOutputStream())));
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            System.out.println("openConnection() --> " + "Connection established successfully!");

            return;
        } catch (UnknownHostException e) {
            System.out.println("openConnection() --> UnknownHostException");
        } catch (IOException e) {
            System.out.println("openConnection() --> IOException");
        } catch (Exception e) {
            System.out.println("openConnection() --> Exception");
            System.out.println(e.toString());
        }

        System.out.println("Connection failed.");
    }

    public boolean isConnected() {
        return conn.isConnected();
    }

    public void closeConnection() {
        System.out.println("Closing connection.");

        try {
            reader.close();

            if (conn != null) {
                conn.close();
                conn = null;
            }
            System.out.println("Connection closed!");
        } catch (IOException e) {
            System.out.println("closeConnection() --> IOException");
        } catch (NullPointerException e) {
            System.out.println("closeConnection() --> NullPointerException");
        } catch (Exception e) {
            System.out.println("closeConnection() --> Exception");
            System.out.println(e.toString());
        }
    }
    public void sendMsg(String msg, String msgType) {
        System.out.println("Sending a message");

        try {
            String outputMsg;
            if (msg == null) {
                //outputMsg = msgType + "\n";
                outputMsg = msgType + "\n";
            } /*else if (msgType.equals(MAP_STRINGS) || msgType.equals(ROBOT_POS)) {
                outputMsg = msgType + " " + msg + "\n";

            }*/ else {
                //outputMsg = msgType + " " + msg + "\n";
                outputMsg = msgType + " " + msg + "\n";
            }

            System.out.println("Sending out message:\n" + outputMsg);
            writer.write(outputMsg);
            writer.flush();
        } catch (IOException e) {
            System.out.println("sendMsg() --> IOException");
            //test
            sendMsg(msg, msgType);
        } catch (Exception e) {
            System.out.println("sendMsg() --> Exception");
            System.out.println(e.toString());
            sendMsg(msg, msgType);
        }
    }
    public String recvMsg() {
        System.out.println("Receiving a message");

        try {
            StringBuilder sb = new StringBuilder();
            String input = reader.readLine();

            if (input != null && input.length() > 0) {
                sb.append(input);
                System.out.println(sb.toString());
                return sb.toString();
            }
        } catch (IOException e) {
            System.out.println("receiveMsg() --> IOException");
        } catch (Exception e) {
            System.out.println("receiveMsg() --> Exception");
            System.out.println(e.toString());
        }

        return null;
    }






}
