package edu.tcu.cs.easybites.userfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilterRepository extends JpaRepository<UserFilter, Integer> {
}
