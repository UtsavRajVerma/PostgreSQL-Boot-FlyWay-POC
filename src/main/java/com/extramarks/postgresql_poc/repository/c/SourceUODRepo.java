package com.extramarks.postgresql_poc.repository.c;

import com.extramarks.postgresql_poc.entity.c.SourceUOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceUODRepo extends JpaRepository<SourceUOD,Integer> {
}