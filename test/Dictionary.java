package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
    private CacheManager existsWords;
    private CacheManager NotexistsWords;
    private BloomFilter b;
    private final String[] textFiles;


    public Dictionary (String... fileNames){
        textFiles = fileNames;
        this.existsWords = new CacheManager(400, new LRU());
        this.NotexistsWords = new CacheManager(100, new LFU());
        b = new BloomFilter(256, "MD5", "SHA1");
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(" "); 
                    for(String w : words)
			            b.add(w);
                }
                reader.close();
            } catch (IOException e){
                throw new RuntimeException("not found");
            }
        }  
    }

    public boolean query(String word)
    {
        return checkWordExistence(word);
    }

    public boolean challenge(String word)
    {
        return searchInTextFiles(word);
    }

    public boolean checkWordExistence(String word) {
        if (existsWords.query(word)) 
            return true;
        
        if (NotexistsWords.query(word)) 
            return false;

        if (b.contains(word)) {
            existsWords.add(word);
            return true;
        } else {
            NotexistsWords.add(word);
            return false;
        }
    }

   
    public boolean searchInTextFiles(String word) {
        boolean isFound = false;
        try {
            isFound = IOSearcher.search(word, textFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFound;
    }
}

    


