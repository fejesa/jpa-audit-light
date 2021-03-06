package com.foo.security;

/**
 * Wraps the user identifier. {@code UserContext} instance can be created after
 * - for example - the authentication.
 *
 */
public final class UserContext {

	private long userId;

	private String userName;

	public UserContext() {
	}

	public UserContext(long userId) {
		this.userId = userId;
	}

	public UserContext(long userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return userName;
	}
}
