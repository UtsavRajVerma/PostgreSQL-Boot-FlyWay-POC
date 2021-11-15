package com.extramarks.postgresql_poc.repository.d;

import com.extramarks.postgresql_poc.entity.d.TargetUOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetUODRepo extends JpaRepository<TargetUOD,Integer> {
}
