package edu.tcu.cs.easybites.nutritionuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionUserRepository extends JpaRepository<NutritionUser, Integer> {
}
