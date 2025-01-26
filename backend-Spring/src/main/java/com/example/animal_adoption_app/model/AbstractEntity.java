package com.example.animal_adoption_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

    @CreatedDate
    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        if (this.created_at == null) {
            this.created_at = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
        }
    }

}
