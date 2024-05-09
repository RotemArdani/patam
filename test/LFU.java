package test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LFU implements CacheReplacementPolicy {
    private LinkedHashMap<String,Integer> wordsUse;
    private int min;

    public LFU (){
        this.wordsUse = new LinkedHashMap<>();
        this.min = 0;
    }

    @Override
    public void add (String word){
        wordsUse.compute(word, (k, v) -> (v == null) ? (1) : (v+1));
        min = 1;
    }

	public String remove() throws NoSuchElementException{
        for (Map.Entry<String, Integer> e : wordsUse.entrySet()){
            if (e.getValue() == min){
                String toRemove = e.getKey();
                if (toRemove != null){
                    min++;
                    return toRemove;
                }
            }
        } 

        throw new NoSuchElementException("Element not fount in cache");
    }
}



