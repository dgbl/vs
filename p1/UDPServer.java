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

            try {
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData()).trim();
                InetAddress IPAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                System.out.println("RECEIVED from " + IPAddress + " : " + sentence);

                //Vergleich Empfangen == Warenliste
                String sentenceCmp = sentence.trim();

                for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
                    if (sentenceCmp.equals(wList.getWare(i).getBezeichnung())) {
                        if (wList.getWare(i).getAnzahl() == 0) {
                            System.out.println(wList.getWare(i).getBezeichnung() + " ist leer");
                        } else {
                            System.out.println(wList.getWare(i).getBezeichnung() + " entnommen");
       
                            //Warenabnahme
                            takeWares(i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                            }
                            //Senden
                            String message = Integer.toString(wList.getWare(i).getAnzahl());
                            byte[] sendData = new byte[message.length() * 8];
                            sendData = message.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
                            serverSocket.send(sendPacket);

                        }
                    } else {
//                    String message = "Keine Ware gefunden";
//                    byte[] sendData = new byte[dataLength];
//                    sendData = message.getBytes();
//                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
//                    serverSocket.send(sendPacket);
                    }

                }

            } catch (IOException e) {
                System.out.println("Could not receive datagram.\n" + e.getLocalizedMessage());
            } //Ausgabe
            displayWares();
        }
    }

    public static void initWares() {

        Ware w1 = new Ware();
        w1.setBezeichnung("Apfel");
        w1.setAnzahl(10);
        w1.setAbnahme(1);
        wList.addWare(w1);

        Ware w2 = new Ware();
        w2.setBezeichnung("Banane");
        w2.setAnzahl(5);
        w2.setAbnahme(2);
        wList.addWare(w2);

        Ware w3 = new Ware();
        w3.setBezeichnung("Milch");
        w3.setAnzahl(5);
        w3.setAbnahme(3);
        wList.addWare(w3);

        Ware w4 = new Ware();
        w4.setBezeichnung("Mango");
        w4.setAnzahl(5);
        w4.setAbnahme(1);
        wList.addWare(w4);

        Ware w5 = new Ware();
        w5.setBezeichnung("Fisch");
        w5.setAnzahl(10);
        w5.setAbnahme(2);
        wList.addWare(w5);
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
