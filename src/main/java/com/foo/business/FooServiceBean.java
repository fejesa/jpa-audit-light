package com.foo.business;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.audit.AuditInterceptor;
import com.foo.model.Foo;
import com.foo.security.UserContext;

@Stateless
public class FooServiceBean implements FooBusiness {

	private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	private EntityManager em;

	/**
	 * Marks the API that it should be audited.
	 */
	@Interceptors({ AuditInterceptor.class })
	@Override
	public void create(String name, UserContext user) {
		log.info("Create Foo - {} - by user {}", name, user.getUserId());
		Foo foo = new Foo();
		foo.setName(name);

		em.persist(foo);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<Foo> getAll() {
		return em.createQuery("select f from Foo f", Foo.class).getResultList();
	}
}