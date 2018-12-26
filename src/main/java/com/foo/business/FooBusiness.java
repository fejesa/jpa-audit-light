package com.foo.business;

import java.util.List;

import com.foo.model.Foo;
import com.foo.security.UserContext;

/**
 * Sample service definition.
 *
 */
public interface FooBusiness {

	/**
	 * Create a business domain entity, execution is audited.
	 * @param name The business info that must be persisted.
	 * @param user Wraps the security info.
	 */
	void create(String name, UserContext user);
	
	/**
	 * In this case data fetching is not audited.
	 * @return
	 */
	List<Foo> getAll();

}
