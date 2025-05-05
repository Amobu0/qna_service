package com.amobu.qna_service.boundedContext.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    @Modifying // INSERT, UPDATE, DELETE 와 같은 데이터가 변경 작업에서만 사용
    @Transactional
    // nativeQuery = true 여아 MySQL 쿼리 사용
    @Query(value = "ALTER TABLE site_user AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();

    Optional<SiteUser> findByUsername(String username);
}
