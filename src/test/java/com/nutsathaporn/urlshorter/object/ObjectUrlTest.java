package com.nutsathaporn.urlshorter.object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ObjectUrlTest {
	
	@Test
	public void constructObjectShouldBeNotNull() {
		ObjectUrl objectUrl = new ObjectUrl("test", 123);
		assertNotNull(objectUrl, "objectUrl should be not null");
	}
	
	@Test
	public void afterConstructObjectFieldLongUrlShouldBeMatchInput() {
		String input = "LongUrl";
		ObjectUrl objectUrl = new ObjectUrl("LongUrl", 123);
		assertEquals(input, objectUrl.getLongUrl());
	}
	
	@Test
	public void afterConstructObjectWithPositiveMinuteExpireShouldBeNotExpire() {
		ObjectUrl objectUrl = new ObjectUrl("LongUrl", 123);
		assertFalse(objectUrl.isExpired(), "positive minute should be not expire");
	}
	@Test
	public void afterConstructObjectWithNatgativeMinuteExpireShouldBeExpire() {
		ObjectUrl objectUrl = new ObjectUrl("LongUrl", -123);
		assertTrue(objectUrl.isExpired(), "positive minute should be expire");
	}
	@Test
	public void afterConstructObjectShouldBeGotShortUrl() {
		ObjectUrl objectUrl = new ObjectUrl("LongUrl", 123);
		assertNotNull(objectUrl.getShortUrl(), "short url should be not null");
		assertEquals(6, objectUrl.getShortUrl().length(), "short url should be length = 6");
	}

	@Test
	public void afterConstructObjectAndRegenerateShortUrlShouldBeGotNewShortUrl() {
		ObjectUrl objectUrl = new ObjectUrl("LongUrl", 123);
		String firstShortUrl = objectUrl.getShortUrl();
		String secondShortUrl = objectUrl.regenerateShortUrl();
		assertNotEquals(firstShortUrl, secondShortUrl, "short url after regenerate shoud be same");
	}
}
