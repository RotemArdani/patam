package test;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
	private int size;
    private BitSet bitset;
    private final MessageDigest[] hashFunctions;

    public BloomFilter (int size, String... algs){
        if (size <= 0)
            throw new IllegalArgumentException();
        this.size = size;
        this.bitset = new BitSet(this.size);
        hashFunctions = new MessageDigest[algs.length];
        for (int i=0;i<algs.length;i++){
            try {
            hashFunctions[i] = MessageDigest.getInstance(algs[i]);
            } catch (NoSuchAlgorithmException a) {
                throw new RuntimeException("invalid alg");
            }
        }
    }

    public void add (String word){
        for (MessageDigest h : hashFunctions){
            byte[] wordHashBts = h.digest(word.getBytes(StandardCharsets.UTF_8));
            int btsAsInt = new BigInteger(wordHashBts).intValue();
            try{
                bitset.set(Math.abs(btsAsInt % size));
            } catch (IndexOutOfBoundsException e){
                throw new RuntimeException("invalid index for bitset");
            }
        }
    }

    public boolean contains (String word){
        if (word == null)
            throw new NullPointerException("Word must not be null");
        for (MessageDigest h : hashFunctions){
            byte[] wordHashBts = h.digest(word.getBytes());
            int btsAsInt = new BigInteger(wordHashBts).intValue();
            if (!bitset.get(Math.abs(btsAsInt % size)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder bitsetString = new StringBuilder();
        for (int i=0;i<bitset.length();i++){
            bitsetString.append(bitset.get(i) ? '1' : '0');
        }
        return bitsetString.toString();
    }
}
