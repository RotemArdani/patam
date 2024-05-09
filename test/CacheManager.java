package test;

import java.util.HashSet;

public class CacheManager {
    private int contains; //the words that actually added to the HashSet
    final private int maxCacheSize;
    private HashSet<String> wordsCache;
    private CacheReplacementPolicy crp;

    public CacheManager (int size, CacheReplacementPolicy crp){
        this.wordsCache = new HashSet<>();
        this.maxCacheSize = size;
        this.crp = crp;
        this.contains = 0;
    }

    public boolean query (String word){
        return wordsCache.contains(word);
    }

    public void add(String word){
        if (contains+1 > maxCacheSize)
            wordsCache.remove(crp.remove());
        wordsCache.add(word);
        crp.add(word);
        contains++;
    }

    public HashSet<String> getCache() {
        return wordsCache;
    }
}
