package com.foo.audit;

import com.foo.security.UserContext;

/**
 * Wraps the {@code UserContext} instance and store it in a thread local
 * instance. It guarantees that every thread can only see its own context. The
 * stored context is set by the {@code AuditInterceptor} and used by the
 * {@code EntityAuditListener}.
 *
 */
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
