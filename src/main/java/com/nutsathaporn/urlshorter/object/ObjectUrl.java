package com.nutsathaporn.urlshorter.object;

import java.util.Random;

public class ObjectUrl {

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    
	private String longUrl;
    private String shortUrl;
    private long expirationTime;

    public ObjectUrl(String longUrl, int expireInMinutes) {
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
    
    public String regenerateShortUrl() {
        this.shortUrl = generateRandomString();
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
}
