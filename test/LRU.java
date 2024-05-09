package test;
import java.util.LinkedHashSet;


public class LRU implements CacheReplacementPolicy {

    private final LinkedHashSet<String> wordsUse;
    private final int maxSize;

    public LRU() {
        this.wordsUse = new LinkedHashSet<>();
        this.maxSize = 100;
    }

    /**
     If the word is already present in the cache, it is removed and re-added to the end of the cache.
     If adding the word would cause the cache to exceed its maximum size, the least recently used word is removed.
     */
    @Override
    public void add(String word) {
        this.wordsUse.remove(word);
        this.wordsUse.add(word);

        if (this.wordsUse.size() > this.maxSize) {
            String leastRecentlyUsed = this.remove();
            this.wordsUse.remove(leastRecentlyUsed);
        }
    }

    /**
     Removes the least recently used word from the cache.
     return the least recently used word that was removed from the cache
     */
    @Override
    public String remove() {
        String[] cacheArr = this.wordsUse.toArray(new String[this.wordsUse.size()]);
        return cacheArr[0];
    }
}

