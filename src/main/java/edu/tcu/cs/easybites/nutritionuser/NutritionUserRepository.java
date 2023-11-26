package edu.tcu.cs.easybites.nutritionuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionUserRepository extends JpaRepository<NutritionUser, Integer> {
    Optional<NutritionUser> findByEmail(String email);
}
