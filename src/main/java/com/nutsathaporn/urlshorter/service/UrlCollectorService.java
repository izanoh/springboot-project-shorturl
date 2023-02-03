package com.nutsathaporn.urlshorter.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.nutsathaporn.urlshorter.object.ObjectUrl;

@Service
public class UrlCollectorService {

	private HashMap<String, ObjectUrl> mapShortUrls;
	private HashMap<String, Integer> hitCounts;
		
	public UrlCollectorService() {
		this.mapShortUrls = new HashMap<>();
		this.hitCounts = new HashMap<>();
	}

	public ObjectUrl getNewObject(String longUrl) {
		return new ObjectUrl(longUrl, 60*24*9999999);
	}
	
	public boolean isShortUrlExist(String shortUrl) {
		return getMapShortUrls().containsKey(shortUrl);
	}
	
	public boolean saveUrl(ObjectUrl objectUrl) {
		if (!isShortUrlExist(objectUrl.getShortUrl())) {
			getMapShortUrls().put(objectUrl.getShortUrl(), objectUrl);
			return true;
		} else {
			return false;
		}
	}
	
	public String getLongUrl(String shortUrl) {
		ObjectUrl objectUrl = mapShortUrls.get(shortUrl);
        if (null == objectUrl) {
            return null;
        }
        if (objectUrl.isExpired()) {
        	getMapShortUrls().remove(shortUrl);
            hitCounts.remove(shortUrl);
            return null;
        }
        hitCounts.put(shortUrl, hitCounts.get(shortUrl) + 1);
        return objectUrl.getLongUrl();
	}
	
	private HashMap<String, ObjectUrl> getMapShortUrls() {
		if (null == mapShortUrls) {
			this.mapShortUrls = new HashMap<>();
		}
		return mapShortUrls;
	}
}
