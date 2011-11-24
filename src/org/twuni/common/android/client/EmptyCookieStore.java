package org.twuni.common.android.client;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class EmptyCookieStore implements CookieStore {

	@Override
	public void addCookie( Cookie cookie ) {
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean clearExpired( Date since ) {
		return false;
	}

	@Override
	public List<Cookie> getCookies() {
		return Collections.emptyList();
	}

}
