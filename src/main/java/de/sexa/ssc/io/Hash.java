package de.sexa.ssc.io;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Hash {

    private static List<String> abc = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");


    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String generateRandomString() {

        int length = 12;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= length; i++) {
            double random = new Random().nextDouble();

            String randomChar;

            if (random < 0.33) {
                int randomIndex = new Random().nextInt(abc.size() - 1);
                randomChar = abc.get(randomIndex);
            } else if (random < 0.66) {
                int randomIndex = new Random().nextInt(abc.size() - 1);
                randomChar = abc.get(randomIndex).toUpperCase();
             } else {
                randomChar = String.valueOf(new Random().nextInt(9));
            }

            sb.append(randomChar);
        }

        return sb.toString();
    }

}
