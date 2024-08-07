package com.example.hrm.repository;

import com.example.hrm.model.ResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetRequestRepo extends JpaRepository<ResetRequest, Long> {
    @Query("select r FROM ResetRequest r where r.username =:username")
    ResetRequest findByUsername(@Param("username") String username);
}
