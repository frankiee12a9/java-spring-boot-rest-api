package com.github.frankiie.springboot.domains.management.entity;

import static com.github.frankiie.springboot.domains.session.service.SessionService.authorized;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static javax.persistence.FetchType.LAZY;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domains.management.model.Entity;
import com.github.frankiie.springboot.domains.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class Auditable implements Entity {
    private static final Logger LOGGER = LoggerFactory.getLogger(Auditable.class);

    @Override
    public abstract Long getId();

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;
    
    @JoinColumn(name = "created_by")
    // @ManyToOne(optional = true, fetch = LAZY)
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private User createdBy;
    
    @JoinColumn(name = "updated_by")
    @ManyToOne(optional = true, fetch = LAZY)
    private User updatedBy;
    
    @JoinColumn(name = "deleted_by")
    @ManyToOne(optional = true, fetch = LAZY)
    private User deletedBy;

    @JsonIgnore
    public Optional<User> getCreatedBy() {
      createdBy = authorized()
        .map(authorized -> new User(authorized.getId()))
            .orElse(null);
      return ofNullable(createdBy);
    }

    @JsonIgnore
    public Optional<User> getUpdatedBy() {
      return ofNullable(updatedBy);
    }

    @JsonIgnore
    public Optional<Long> getDeletedBy() {
        return ofNullable(deletedBy.getId());
    }

    @JsonIgnore
    public Optional<Long> getCreatedById() {
        createdBy = authorized()
        .map(authorized -> new User(authorized.getId()))
            .orElse(null);
        return ofNullable(createdBy.getId());
    }

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getUsedAt() {
      return this.usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
      this.usedAt = usedAt;
    }

    public Boolean isActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @PrePersist
    private void save() {
        this.createdAt = now();
        this.usedAt = now();
        this.createdBy = authorized()
        .map(authorized -> new User(authorized.getId()))
            .orElse(null);
    }

    @PreUpdate
    private void update() {
        this.updatedAt = now();
        this.usedAt = now();
        this.updatedBy = authorized()
        .map(authorized -> new User(authorized.getId()))
            .orElse(null);
    }

}