package com.foo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@SequenceGenerator(name = "id_gen", sequenceName = "id_sequence", allocationSize = 1)
	@GeneratedValue(generator = "id_gen", strategy = GenerationType.SEQUENCE)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
