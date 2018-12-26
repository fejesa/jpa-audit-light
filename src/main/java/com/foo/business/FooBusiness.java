package com.foo.business;

import java.util.List;

import com.foo.model.Foo;
import com.foo.security.UserContext;

public interface FooBusiness {

	void create(String name, UserContext user);
	
	List<Foo> getAll();

}
