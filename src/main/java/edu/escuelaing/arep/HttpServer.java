package edu.escuelaing.arep;

import java.io.*;
import java.net.*;

public class HttpServer {
    private static String city;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            String outputLine = null;
            boolean primeraLinea = true;
            String file = "";
            while ((inputLine = in.readLine()) != null) { 
                System.out.println("Recibí: " + inputLine);
                if (primeraLinea) {
                    file = inputLine.split(" ")[1];
                    System.out.println("File: " + file);
                    primeraLinea = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            if (file.startsWith("/clima")) {
                outputLine = getResource(inputLine);
            }else if(file.startsWith("/consulta?ciudad=")) {
                outputLine = getResourceByCity(inputLine, args);
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String getResourceByCity(String inputLine, String[] args) throws IOException {
        String[] arg = new String[0];
        String outputLine = "api.openweathermap.org/data/2.5/weather?q="+arg[0]+"&appid=355d2ea2624b1de762da6321c9e7b329";
        URL url = new URL(outputLine);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        outputLine = response.toString();
        return outputLine;
    }

    private static String getResource(String inputLine) throws IOException {
        String outputLine = "https://openweathermap.org/";
        URL url = new URL(outputLine);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        outputLine = response.toString();
        return outputLine;
    }

}