/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vsp2;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author DNS
 */
public class Webserver {

    public static void main(String argv[]) throws Exception {

        int port = 9999;
        System.out.println("Webserver start | Port: " + port + " " + InetAddress.getLocalHost());
        
        //Socket erstellen und bind()
        ServerSocket serverSocket = new ServerSocket(port);

        //HTTP Request: listen() für Verbingungsanfrage
        //Erstellt HTTP-Anfrage
        //Erstellen und starten von Thread für Request
        while (true) {
            Socket connectionSocket = serverSocket.accept();
            HttpRequest request = new HttpRequest(connectionSocket);

            Thread thread = new Thread(request);
            thread.start();
        }
    }
}
//Construktor für HTTP Anfrage
final class HttpRequest implements Runnable {

    //CR und Line Feed (Windows, in Linux nur \n)
    final static String CRLF = "\r\n";
    Socket socket;
    public HttpRequest(Socket socket) throws Exception {
        this.socket = socket;
    }

    //Threadprozess
    public void run() {

        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //Anfrageverarbeitung
    private void processRequest() throws Exception {
        //Input- und OutputStreams des Sockets
        InputStream instream = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        //liest Input
        BufferedReader br = new BufferedReader(new InputStreamReader(instream));

        //GET /index.html HTTP/1.1
        String requestLine = br.readLine();
        System.out.println();
        System.out.println(requestLine);
        
        //Zerlegen der Anfrage in einzelne Elemente und überspringt "GET" --> Filename
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken();
        String fileName = tokens.nextToken();

        //Setzt "." --> damit Datei im aktuellen Verzeichnis
        fileName = "." + fileName;

        //Datei öffnen falls im Verzeichnis existent
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }

        //Response erstellen (200: OK sonst 404: nicht gefunden)
        String statusLine = null;
        String contentTypeLine = null;
        String errorMessage = null;

        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF; 
            contentTypeLine = "Content-type: text/html" + CRLF;
        }
        else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content-type: " + "text/html" + CRLF;
            errorMessage = "HTML-Datei nicht gefunden!";
        }

        //Status, Content Type und Leerzeile nach dem Header schreiben 
        os.writeBytes(statusLine);
        os.writeBytes(contentTypeLine);
        os.writeBytes(CRLF);

        //Sendet Datei
        if (fileExists) {
            sendBytes(fis, os);
            //os.writeBytes(statusLine);
            //os.writeBytes(contentTypeLine);
            fis.close();
        } else {
            os.writeBytes(statusLine);
            os.writeBytes(errorMessage);
            os.writeBytes(contentTypeLine);
        }

        //Ausgabe Request
        System.out.println(fileName);
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        //OutputStream, BufferedReader und Socket schließen.
        os.close();
        br.close();
        socket.close();

    }

    //Sendet Bytes für Input und OutputStream
    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
        byte[] buffer = new byte[1024];
        int bytes = 0;

        //Schreibt Anfrage in den OutputStream
        while ((bytes = fis.read(buffer)) != -1)
        {
            os.write(buffer, 0, bytes);
        }
    }
}
