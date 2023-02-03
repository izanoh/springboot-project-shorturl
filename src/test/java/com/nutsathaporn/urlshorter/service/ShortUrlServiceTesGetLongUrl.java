package com.nutsathaporn.urlshorter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShortUrlServiceTesGetLongUrl {
	
	@Mock
	private UrlCollectorService collectorService;
	
	private ShortUrlService shortUrlService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		shortUrlService = new ShortUrlService(collectorService);
	}
	
	@Test
	public void givenEmptyShortUrl_whenGetLongUrl_thenReturnError() throws Exception {
		Exception exception = Assertions.assertThrows(Exception.class,()-> shortUrlService.getLongUrl(""));
		Assertions.assertEquals("shortUrl should not null or empty", exception.getMessage());
	}
	
	@Test
	public void givenNullShortUrl_whenGetLongUrl_thenReturnError() throws Exception {
		Exception exception = Assertions.assertThrows(Exception.class,()-> shortUrlService.getLongUrl(null));
		Assertions.assertEquals("shortUrl should not null or empty", exception.getMessage());
	}
	
	@Test
	public void givenShortUrl_whenGetLongUrl_NotInList_thenReturnError() throws Exception {
		Exception exception = Assertions.assertThrows(Exception.class,()-> shortUrlService.getLongUrl("test"));
		Assertions.assertEquals("shortUrl not found", exception.getMessage());
	}
	
	@Test
	public void givenShortUrl_whenGetLongUrl_InList_thenReturnLongUrl() throws Exception {
		String expectLongUrl = "long";
		BDDMockito.given(collectorService.getLongUrl("test")).willReturn(expectLongUrl);
		String longUrl = shortUrlService.getLongUrl("test");
		Assertions.assertEquals(expectLongUrl, longUrl);
	}
}
