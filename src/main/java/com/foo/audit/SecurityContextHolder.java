package com.foo.audit;

import com.foo.security.UserContext;

public class SecurityContextHolder {

	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

	public static UserContext getContext() {
		return userContext.get();
	}

	public static void setContext(UserContext context) {
		userContext.set(context);
	}

	public static void removeContext() {
		userContext.remove();
	}
}
