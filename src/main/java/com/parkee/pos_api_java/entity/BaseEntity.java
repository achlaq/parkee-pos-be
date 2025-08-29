package com.parkee.pos_api_java.entity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid-sequence")
    @GenericGenerator(name = "uuid-sequence", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "created_by", updatable = false, length = 50)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "deleted_by", length = 50)
    private String deletedBy;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    public void markDeleted(String username) {
        this.deletedBy = username;
        this.deletedDate = LocalDateTime.now();
    }

    public void restore() {
        this.deletedBy = null;
        this.deletedDate = null;
    }

    public void setCreatedByUser(String username) {
        this.createdBy = username;
        this.createdDate = LocalDateTime.now();
    }

    public void setUpdatedByUser(String username) {
        this.updatedBy = username;
        this.updatedDate = LocalDateTime.now();
    }
}

