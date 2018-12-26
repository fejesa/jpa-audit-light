package com.foo.audit;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.foo.model.AuditedEntity;
import com.foo.security.UserContext;

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

	private Long getUserId() {
		UserContext userContext = SecurityContextHolder.getContext();
		if (userContext != null) {
			return userContext.getUserId();
		}
		return null;
	}
}
