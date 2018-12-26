package com.foo.business;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.foo.audit.EntityAuditListener;
import com.foo.model.BaseEntity;
import com.foo.resource.GenericProducer;
import com.foo.security.UserContext;

@RunWith(Arquillian.class)
public class BusinessTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "jpa-sample.war")
				.addPackage(FooBusiness.class.getPackage())
				.addPackage(GenericProducer.class.getPackage())
				.addPackage(BaseEntity.class.getPackage())
				.addPackage(EntityAuditListener.class.getPackage())
				.addPackage(UserContext.class.getPackage())
				.addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/orm.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private FooBusiness business;

	@Test
	public void createTest() {
		String name = UUID.randomUUID().toString();
		business.create(name, getUserContext());

		assertTrue(business.getAll().stream().anyMatch(f -> f.getName().equals(name)));
	}
	
	private UserContext getUserContext() {
		return new UserContext(1, "fake");
	}
}