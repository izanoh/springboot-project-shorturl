package com.nutsathaporn.urlshorter.object;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class ObjectUrl2 {

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    
	private String longUrl;
    private String shortUrl;
    private long expirationTime;

    public ObjectUrl2(String longUrl, int expireInMinutes) {
        this.longUrl = longUrl;
        this.shortUrl = generateRandomString();
        this.expirationTime = System.currentTimeMillis() + expireInMinutes * 60 * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
    
    public String generateSHA256(String longUrl) {
        try {
        	MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(longUrl.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String shortUrl = bigInt.toString(16);
            System.out.println(shortUrl);
            // convert to upper and lowercase alphanumeric characters
            shortUrl = shortUrl.replaceAll("[^A-Za-z0-9]", "");
            return shortUrl.substring(0,8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
