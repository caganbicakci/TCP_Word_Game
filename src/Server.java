import java.net.*;
import java.io.*;
import java.util.Timer;

public class Server extends Calculation {

    public static void main(String[] args) throws IOException {

        Server obj = new Server();


        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            System.err.println("I/O exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Server started. Waiting for the connection...");
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept(); // bağlantı gelene kadar burada bekler
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println(clientSocket.getLocalAddress() + " baglandi.");

        // input stream ve output stream yaratılıyor...
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine,outputLine;

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        while ((inputLine = in.readLine()) != null){ // istemciden gelen string okunuyor... //inputline clientdan gelen


            if(inputLine.equals("You win!")){
                System.out.println("You Win Congratulations!");
                break;
            }


            obj.addToUsedWords(inputLine); //clientdan gelen inputu arraye attım

            String lastTwoClient = inputLine.substring(inputLine.length()-2,inputLine.length());

            System.out.println("WORD FROM CLIENT: " + inputLine + " ENTER THE WORD THAT START WITH THESE WORDS: " + lastTwoClient) ;
            System.out.println("YOUR TURN!");
            System.out.println("USED WORDS: "+ obj.usedWords);

            //obj.setTimer(); //düzgün çalışmıyor :(

            outputLine = stdIn.readLine();  //satırda yazılanı göndermek için outputline a eşitledik

            if(!lastTwoClient.equals(outputLine.substring(0,2))){
                out.println("You win!");
                System.out.println("Game over! Client wins!");
                break;
            }

            if(obj.usedWords.contains(outputLine)){
                out.println("You win!");
                System.out.println("Game over! Client wins!");
                break;
            }

            out.println(outputLine); // serverdan clienta gönderilen cevap

            obj.addToUsedWords(outputLine);// girilen inputu daha sonra karşılaştırmak için arraye ekle



        }


        System.out.println(clientSocket.getLocalSocketAddress()
                + " connection stopped.");
        // stream ve socketleri kapat.
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}