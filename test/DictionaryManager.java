package test;

import java.util.HashMap;

public class DictionaryManager {
    private static DictionaryManager single_instance = null;
    HashMap<String,Dictionary> dictionaries;

    public DictionaryManager () {
        dictionaries = new HashMap<String, Dictionary>();
    }

    public int getSize(){
        return dictionaries.size();
    }

    public static DictionaryManager get(){
        if (single_instance == null)
            single_instance = new DictionaryManager();

        return single_instance;
    }

    public boolean query (String... args){
        String wordToSearch = args [args.length - 1];
        int i = 0;
        boolean wordExits = false;
        for (String file : args) {
            if (i != args.length - 1) {
                if (!dictionaries.containsKey(file)){
                    dictionaries.put(file, new Dictionary(file));
                } 
                if (dictionaries.get(file).query(wordToSearch)) {
                    wordExits = true;
                    //not returning because we want that the other dictionaries will update as well in their query method
                    //will save time in future queries
                }
            }
            i++;
        }
        return wordExits;
    }

    public boolean challenge (String... args){
        String wordToSearch = args [args.length - 1];
        int i = 0;
        boolean wordExits = false;
        for (String file : args) {
            if (i != args.length - 1) {
                if (!dictionaries.containsKey(file)){
                    dictionaries.put(file, new Dictionary(file));
                } 
                if (dictionaries.get(file).challenge(wordToSearch)) {
                    wordExits = true;
                    //not returning because we want that the other dictionaries will update as well in their query method
                    //will save time in future queries
                }
            }
            i++;
        }
        return wordExits;
    }

}
