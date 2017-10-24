package udp;

import java.io.*;
import java.net.*;

class UDPServer {

    private static WareList wList = new WareList();

    public static void main(String args[]) throws Exception {

        int port = 9876;
        int dataLength = 100;

        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[dataLength];

        System.out.println("*****************************************************");
        System.out.println("*******************    ZENTRALE    ******************");
        System.out.println("*****************************************************");
        System.out.println("Datagram Socket Port: " + port);

        initWares();
        displayWares();

        while (true) {
            System.out.println("Wartet....");
            //Empfangen
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData()).trim();
            InetAddress IPAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            System.out.println("RECEIVED from " + IPAddress + " : " + sentence);

            //Vergleich Empfangen == Warenliste
            String sentenceCmp = sentence.trim();

            for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
                if (sentenceCmp.equals(wList.getWare(i).getBezeichnung())) {
                    System.out.println(wList.getWare(i).getBezeichnung() + " entnommen");
                    //Warenabnahme
                    takeWares(i);
                    //Senden
                    String message = Integer.toString(wList.getWare(i).getAnzahl());
                    byte[] sendData = new byte[message.length()*8];
                    sendData = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
                    serverSocket.send(sendPacket);
                  
                } else {
//                    String message = "Keine Ware gefunden";
//                    byte[] sendData = new byte[dataLength];
//                    sendData = message.getBytes();
//                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
//                    serverSocket.send(sendPacket);
                }
            }
            //Ausgabe
            displayWares();       
        }
    }

    public static void initWares() {

        Ware w1 = new Ware();
        w1.setBezeichnung("A");
        w1.setAnzahl(25);
        w1.setAbnahme(8);
        wList.addWare(w1);

        Ware w2 = new Ware();
        w2.setBezeichnung("B");
        w2.setAnzahl(25);
        w2.setAbnahme(20);
        wList.addWare(w2);

        Ware w3 = new Ware();
        w3.setBezeichnung("C");
        w3.setAnzahl(25);
        w3.setAbnahme(10);
        wList.addWare(w3);

        Ware w4 = new Ware();
        w4.setBezeichnung("D");
        w4.setAnzahl(25);
        w4.setAbnahme(1);
        wList.addWare(w4);
    }

    public static void takeWares(int ware) throws IOException {

        wList.getWare(ware).entnahme();
        if (wList.getWare(ware).getAnzahl() < 0) {
            wList.getWare(ware).setAnzahl(0);
        }

    }

    public static void displayWares() {
        System.out.println("Fuellbestand: ");
        for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
            System.out.println("     " + wList.getWare(i).getBezeichnung() + ": " + wList.getWare(i).getAnzahl());
        }

    }

}
