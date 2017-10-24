/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp3;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;

class UDPClient {

    public static void main(String args[]) throws Exception {

        boolean exitWhile = true;
        String serverHostName = "localhost";
        int serverPort = 9876;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(serverHostName);
        byte[] sendData = null;
        byte[] receiveData = new byte[1024];
        
        System.out.println("*****************************************************");
        System.out.println("********************    SENSOR    *******************");
        System.out.println("*****************************************************");
        System.out.println("[UDP Client] Attempting to send to " + IPAddress);
        
        System.out.println("Warenbezeichnung: ");
        String bez = inFromUser.readLine();
        sendData = new byte[bez.length() * 8];
                sendData = bez.getBytes();
                DatagramPacket sendPacketBez = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
                clientSocket.send(sendPacketBez);
                
                DatagramPacket receivePacketBez = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacketBez);
                String receivedFromServerBez = new String(receivePacketBez.getData());
                System.out.println("[UDP Client] Fuellbestand \n" + receivedFromServerBez);

        while (exitWhile) {
            
            System.out.println("             Press (m) to start and (.) to stop ");
            
            
            String sentence = inFromUser.readLine();

            if (sentence.equals(".")) {
                exitWhile = false;
            }
            if (sentence.equals("m")) {
                sendData = new byte[sentence.length() * 8];
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
                clientSocket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String receivedFromServer = new String(receivePacket.getData());
                System.out.println("[UDP Client] Fuellbestand \n" + receivedFromServer);

            } else {
                System.out.println("[UDP Client] Unknowen command \n");
            }

        }
        clientSocket.close();

    }

}
