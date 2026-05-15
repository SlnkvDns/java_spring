package ru.omstu.figprogwork.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface CacheRepository extends JpaRepository<CacheEntity, String> {

    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime time);
}
