package vsp2;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DNS
 */
class Zentrale {

    private static WareList wList = new WareList();

    public static void main(String args[]) {

        int port = 9876;
        int dataLength = 100;

        System.out.println("*****************************************************");
        System.out.println("*******************    ZENTRALE    ******************");
        System.out.println("*****************************************************");
        System.out.println("Datagram Socket Port: " + port + "\n");

        initWares();
        displayWares();
        
        //Erstellt Verbindung Zentrale, Seonsor, Webserver
        try {
            udpSocket(port, dataLength);
            Webserver ws = new Webserver();
        } catch (SocketException ex) {
            Logger.getLogger(Zentrale.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    //Kommunikation mit Sensor (Client)
    public static void udpSocket(int port, int dataLength) throws SocketException {
        HtmlFileMaker hfm = new HtmlFileMaker();
        DatagramSocket serverSocket = new DatagramSocket(port);

        byte[] receiveData = new byte[dataLength];
        while (true) {
            System.out.println("Zentrale Wartet....");
            //Empfangen
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress IPAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                System.out.println("RECEIVED from " + IPAddress + " : " + sentence);

                //Vergleich Empfangen == Warenliste
                String sentenceCmp = sentence.trim();

                for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
                    if (sentenceCmp.equals(wList.getWare(i).getBezeichnung())) {
                        if (wList.getWare(i).getAnzahl() == 0) {
                            System.out.println(wList.getWare(i).getBezeichnung() + " ist leer");
                            
                            //Nachfuellen, sobald Warenbestand 0 erreicht
                            //refillTest(i);
                        }

                        //Simulierte Warenabnahme
                        takeWares(i);
                        
                        //Erstellt html-Datei mit aktuellem Warenbestand
                        hfm.makeFile(wList.getWare(0).getAnzahl(), wList.getWare(1).getAnzahl(),wList.getWare(2).getAnzahl(),wList.getWare(3).getAnzahl(), wList.getWare(4).getAnzahl());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                        
                        //Senden an Client 
                        String message = Integer.toString(wList.getWare(i).getAnzahl());
                        byte[] sendData = new byte[message.length() * 8];
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

            } catch (IOException e) {
                System.out.println("Could not receive datagram.\n" + e.getLocalizedMessage());
            } //Ausgabe
            displayWares();    
        }

    }

    //Initialisiert den Warenbestand im Kuehlschrank
    public static void initWares() {

        Ware w1 = new Ware();
        w1.setBezeichnung("Apfel");
        w1.setAnzahl(50);
        w1.setAbnahme(1);
        wList.addWare(w1);

        Ware w2 = new Ware();
        w2.setBezeichnung("Banane");
        w2.setAnzahl(50);
        w2.setAbnahme(2);
        wList.addWare(w2);

        Ware w3 = new Ware();
        w3.setBezeichnung("Birne");
        w3.setAnzahl(50);
        w3.setAbnahme(3);
        wList.addWare(w3);

        Ware w4 = new Ware();
        w4.setBezeichnung("Mango");
        w4.setAnzahl(50);
        w4.setAbnahme(1);
        wList.addWare(w4);

        Ware w5 = new Ware();
        w5.setBezeichnung("Durian");
        w5.setAnzahl(50);
        w5.setAbnahme(2);
        wList.addWare(w5);
    }

    //Entnahme der Ware
    public static void takeWares(int ware) {
        wList.getWare(ware).entnahme();
        if (wList.getWare(ware).getAnzahl() <= 0) {
            wList.getWare(ware).setAnzahl(0);
        }
        System.out.println(wList.getWare(ware).getBezeichnung() + " entnommen");

    }

    //Anzeigen des Kuehlschrnakinhalts
    public static void displayWares() {
        System.out.println("Fuellbestand: ");
        for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
            System.out.println("     " + wList.getWare(i).getBezeichnung() + ": " + wList.getWare(i).getAnzahl());
        }

    }

    //Nachfuellen der Ware bei leerem Bestand
    public static void refillTest(int ware) {
        wList.getWare(ware).setAnzahl(10);
        System.out.println(wList.getWare(ware).getAnzahl() + " ME " + wList.getWare(ware).getBezeichnung() + " hinzugefuegt");
    }


}
