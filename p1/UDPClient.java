package udp;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;

class UDPClient {

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
        System.out.println("(.) to stop ");
        //String sentence = inFromUser.readLine();

        while (sensorWhile) {
            //Eingabe
            System.out.println("Ware f. Sensor eingeben: ");
            String sentence = inFromUser.readLine().trim();
            //Eingabe pruefen
            if (sentence.equals(".")) {
                sensorWhile = false;
            } else if (sentence.equals("Apfel") || sentence.equals("Banane") || sentence.equals("Milch") || sentence.equals("Mango") || sentence.equals("Fisch")) {
                while (entnahmeWhile) {
                    byte[] sendData = new byte[sentence.length()*8];
                    byte[] receiveData = new byte[sentence.length()*8];
                    //Senden
                    sendData = sentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
                    clientSocket.send(sendPacket);
                    //Empfangen
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    receivedFromServer = new String(receivePacket.getData()).trim();
                    //Ausgabe
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
