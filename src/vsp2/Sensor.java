package vsp2;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import static java.lang.Thread.sleep;

/**
 *
 * @author DNS
 */
class Sensor {

    public static void main(String args[]) throws Exception {

        boolean sensorWhile = true;
        boolean entnahmeWhile = true;
        String serverHostName = "localhost";
        int serverPort = 9876;
        int dataLength = 100;
        String receivedFromServer;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(serverHostName);

        System.out.println("*****************************************************");
        System.out.println("********************    SENSOR    *******************");
        System.out.println("*****************************************************");
        System.out.println("IPAdress: " + IPAddress + " | ServerPort: " + serverPort);
        System.out.println("enter 'close' to close sensor");
        //String sentence = inFromUser.readLine();

        while (sensorWhile) {
            //Eingabe
            System.out.println("Ware f. Sensor eingeben: ");
            String sentence = inFromUser.readLine().trim();
            //Eingabe pruefen
            if (sentence.equals("close")) {
                sensorWhile = false;
            } else if (sentence.equals("Apfel") || sentence.equals("Banane") || sentence.equals("Birne") || sentence.equals("Mango") || sentence.equals("Durian")) {
                while (entnahmeWhile) {
                    byte[] sendData = new byte[sentence.length()*8];
                    byte[] receiveData = new byte[sentence.length()*8];
                    
                    //Senden an Zentrale
                    sendData = sentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
                    clientSocket.send(sendPacket);
                    //Empfangen
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    receivedFromServer = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    
                    //Ausgabe des Fuellbestands
                    System.out.println("Fuellbestand " + receivedFromServer);
//                    if(receivedFromServer.equals("0")){
//                        entnahmeWhile = false;
//                    }
                }
            } else {
                System.out.println("Ungueltige Eingabe.....");
            }

        }
        clientSocket.close();

    }
}
