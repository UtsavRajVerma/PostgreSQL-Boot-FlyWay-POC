package com.extramarks.postgresql_poc.repository.a;

import com.extramarks.postgresql_poc.entity.a.SourceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceUserRepo extends JpaRepository<SourceUser,Integer> {
}