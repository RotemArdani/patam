package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler{
    DictionaryManager dm;
    PrintWriter outClient;
    Scanner inClient;

    @Override
    public void handleClient (InputStream inFromclient, OutputStream outToClient) {
        
            inClient = new Scanner(inFromclient);
            outClient = new PrintWriter(outToClient);

            String[] inputFromClient = inClient.next().split(",");
            boolean wordExists = false;
            dm = new DictionaryManager();

            if(inputFromClient.equals('Q')){
                //Deletes the 'Q' from the array, calling quary with just the files names and the word to check
                wordExists = dm.query(Arrays.copyOfRange(inputFromClient, 1, inputFromClient.length));
            } else {//'C'
                //Deletes the 'C' from the array, calling challenge with just the files names and the word to check
                wordExists = dm.challenge(Arrays.copyOfRange(inputFromClient, 1, inputFromClient.length));
            }

            outClient.println(wordExists ? "true" : "false");
            outClient.flush();
        
    }
    

	public void close(){
        inClient.close();
        outClient.close();
    }
}
