/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp3;

import java.io.*;
import java.net.*;

class UDPServer {

    private static WareList wList = new WareList();
    private static Ware w = new Ware();

    public static void main(String args[]) throws Exception {

        int port = 9876;
        int dataLength = 100;

        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[dataLength];

        System.out.println("*****************************************************");
        System.out.println("*******************    ZENTRALE    ******************");
        System.out.println("*****************************************************");
        System.out.println("[UDP Server] Datagram Socket started on Port: " + port);

        //initWares();
        //displayWares();
        DatagramPacket receivePacketBez = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacketBez);
            String bez = new String(receivePacketBez.getData());

            InetAddress IPAddress = receivePacketBez.getAddress();
            int clientPort = receivePacketBez.getPort();

            System.out.println("             RECEIVED from " + IPAddress + ": " + bez);
            w.setBezeichnung(bez);
        
        
        

        while (true) {
            System.out.println("[UDP Server] Waiting for incoming messages on port: " + port);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());

            //InetAddress IPAddress = receivePacket.getAddress();
            clientPort = receivePacket.getPort();

            System.out.println("             RECEIVED from " + IPAddress + ": " + sentence);
            w.setBezeichnung(sentence);
            
            //takeWares();

            String content = wList.getWare(0).getBezeichnung() + ": " + wList.getWare(0).getAnzahl() + "\n";

//            String message = new String(receivePacket.getData());
//            message = message.trim();
//            
            String message = content.trim();
            byte[] sendData = new byte[dataLength];
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
            serverSocket.send(sendPacket);
        }
    }

    public static void initWares() {

        Ware w1 = new Ware();
        w1.setBezeichnung("Ware 1");
        w1.setAnzahl(5);
        w1.setAbnahme(1);
        wList.addWare(w1);

        Ware w2 = new Ware();
        w2.setBezeichnung("Ware 2");
        w2.setAnzahl(22);
        w2.setAbnahme(2);
        wList.addWare(w2);

        Ware w3 = new Ware();
        w3.setBezeichnung("Ware 3");
        w3.setAnzahl(10);
        w3.setAbnahme(1);
        wList.addWare(w3);

        Ware w4 = new Ware();
        w4.setBezeichnung("Ware 4");
        w4.setAnzahl(10);
        w4.setAbnahme(2);
        wList.addWare(w4);
    }

    public static void takeWares() throws IOException {

        for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
            wList.getWare(i).entnahme();
            if (wList.getWare(i).getAnzahl() < 0) {
                wList.getWare(i).setAnzahl(0);
            }
        }

        //System.out.println("     " + wList.getWare(0).getBezeichnung() + ": " + wList.getWare(0).getAnzahl());
//        wList.getWare(1).entnahme();
//        if (wList.getWare(1).getAnzahl() <= 0) {
//            wList.getWare(1).setAnzahl(0);
//        }
        //System.out.println("            " + wList.getWare(1).getBezeichnung() + ": " + wList.getWare(1).getAnzahl());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
    }

    public static void displayWares() {
        System.out.println("Fuellbestand: ");
        for (int i = 0; i <= wList.sizeOfWare() - 1; i++) {
            System.out.println("     " + wList.getWare(i).getBezeichnung() + ": " + wList.getWare(i).getAnzahl());
        }

    }

}
