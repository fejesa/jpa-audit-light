package com.foo.audit;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.foo.model.AuditedEntity;
import com.foo.security.UserContext;

/**
 * JPA listener when called when the entity state is changed.
 * It sets the entity modifier user.
 */
public class EntityAuditListener {

	@PrePersist
	public void auditPersist(AuditedEntity entity) {
		entity.setCreationTime(LocalDateTime.now());
		// It is possible that the user id is already set
		if (entity.getCreatedBy() == null) {
			entity.setCreatedBy(getUserId());
		}
	}

	@PreUpdate
	public void auditUpdate(AuditedEntity entity) {
		entity.setModificationTime(LocalDateTime.now());
		Long userId = getUserId();
		if (userId != null) {
			entity.setModifiedBy(userId);
		}
	}

	/**
	 * Read the security info from the context.
	 */
	private Long getUserId() {
		UserContext userContext = SecurityContextHolder.getContext();
		if (userContext != null) {
			return userContext.getUserId();
		}
		return null;
	}
}