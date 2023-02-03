package com.nutsathaporn.urlshorter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nutsathaporn.urlshorter.object.ObjectUrl;

public class ShortUrlServiceTest {
	
	@Mock
	private UrlCollectorService collectorService;
	
	private ShortUrlService shortUrlService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		shortUrlService = new ShortUrlService(collectorService);
	}
	
	@Test
	public void generateShortUrlShouldBeGotShortUrl() throws Exception {
		BDDMockito.given(collectorService.getNewObject("test")).willReturn(new ObjectUrl("test", 123));
		BDDMockito.given(collectorService.isShortUrlExist(anyString())).willReturn(true,true,false);
		BDDMockito.given(collectorService.saveUrl(any())).willReturn(true);
		String shortUrl = shortUrlService.generateShortUrl("test");
		assertNotNull(shortUrl, "short url should be not null");
	}

	@Test
	public void generateShortUrlShouldBeGotShortUrlWithSameMock() throws Exception {
		ObjectUrl objectUrlMock = new ObjectUrl("test", 123);
		String shortUrlMock = objectUrlMock.getShortUrl();
		BDDMockito.given(collectorService.getNewObject("test")).willReturn(objectUrlMock);
		BDDMockito.given(collectorService.isShortUrlExist(anyString())).willReturn(false);
		BDDMockito.given(collectorService.saveUrl(any())).willReturn(true);
		String shortUrl = shortUrlService.generateShortUrl("test");
		assertEquals(shortUrlMock, shortUrl, "short url should be same mock object");
	}
	

	@Test
	public void generateShortUrlWithAlreadyInListShouldBeGotShortUrlNotSameMock() throws Exception {
		// case got same
		ObjectUrl objectUrlMock = new ObjectUrl("test", 123);
		String shortUrlMock = objectUrlMock.getShortUrl();
		BDDMockito.given(collectorService.getNewObject("test")).willReturn(objectUrlMock);
		BDDMockito.given(collectorService.isShortUrlExist(anyString())).willReturn(true,false);
		BDDMockito.given(collectorService.saveUrl(any())).willReturn(true);
		String shortUrl = shortUrlService.generateShortUrl("test");
		assertNotEquals(shortUrlMock, shortUrl, "short url should be not same mock object");
	}
	

	@Test
	public void generateShortUrlWithEmptyShortUrlShouldBeErrorException() throws Exception {
		Exception exception = Assertions.assertThrows(Exception.class,()-> shortUrlService.generateShortUrl(""));
		Assertions.assertEquals("longUrl should not null or empty", exception.getMessage());
	}

	@Test
	public void generateShortUrlWithNullShortUrlShouldBeErrorException() throws Exception {
		Exception exception = Assertions.assertThrows(Exception.class,()-> shortUrlService.generateShortUrl(null));
		Assertions.assertEquals("longUrl should not null or empty", exception.getMessage());
	}

	@Test
	public void generateShortUrlButCanNotSaceShouldBeNull() throws Exception {
		ObjectUrl objectUrlMock = new ObjectUrl("test", 123);
		BDDMockito.given(collectorService.getNewObject("test")).willReturn(objectUrlMock);
		BDDMockito.given(collectorService.isShortUrlExist(anyString())).willReturn(false);
		BDDMockito.given(collectorService.saveUrl(any())).willReturn(false);
		String shortUrl = shortUrlService.generateShortUrl("test");
		assertNull(shortUrl, "if save url return false. short url shoule be null");
	}
}
