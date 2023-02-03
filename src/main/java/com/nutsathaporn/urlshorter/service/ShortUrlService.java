package com.nutsathaporn.urlshorter.service;

import org.springframework.stereotype.Service;

import com.nutsathaporn.urlshorter.object.ObjectUrl;

@Service
public class ShortUrlService {
	private UrlCollectorService collectorService;

	public ShortUrlService(UrlCollectorService collectorService) {
		this.collectorService = collectorService;
	}
	
	public String generateShortUrl(String longUrl) throws Exception {
		if (null == longUrl || "".equals(longUrl.trim())) {
			throw new Exception("longUrl should not null or empty");
		}
		ObjectUrl objectUrl = collectorService.getNewObject(longUrl);
		while (collectorService.isShortUrlExist(objectUrl.getShortUrl())) {
			objectUrl.regenerateShortUrl();
		}
		
		if (!collectorService.saveUrl(objectUrl)) {
			return null;
		}
				
		return objectUrl.getShortUrl();
	}
	
	public String getLongUrl(String shortUrl) throws Exception {
		if (null == shortUrl || "".equals(shortUrl.trim())) {
			throw new Exception("longUrl should not null or empty");
		}
		String longUrl = collectorService.getLongUrl(shortUrl);
		if (null == longUrl) {
			throw new Exception("shortUrl not found");
		}
		return longUrl;
	}
}
