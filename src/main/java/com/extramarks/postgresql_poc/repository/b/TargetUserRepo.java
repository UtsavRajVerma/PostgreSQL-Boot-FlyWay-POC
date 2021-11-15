package com.extramarks.postgresql_poc.repository.b;

import com.extramarks.postgresql_poc.entity.b.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetUserRepo extends JpaRepository<TargetUser,Integer> {
}
