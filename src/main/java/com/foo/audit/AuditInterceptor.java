package com.foo.audit;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.foo.security.UserContext;

/**
 * Sets the security context for service APIs that use {@code UserContext}.
 *
 */
public class AuditInterceptor {

	@AroundInvoke
	public Object call(InvocationContext context) throws Exception {
		for (Object param : context.getParameters()) {
			if (param instanceof UserContext) {
				SecurityContextHolder.setContext((UserContext) param);
				break;
			}
		}

		try {
			return context.proceed();
		} finally {
			SecurityContextHolder.removeContext();
		}
	}
}
