package edu.tcu.cs.easybites.nutritionuser;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NutritionUserService {

    private final NutritionUserRepository nutritionUserRepository;

    public NutritionUserService(NutritionUserRepository nutritionUserRepository) {
        this.nutritionUserRepository = nutritionUserRepository;
    }

    public List<NutritionUser> findAll(){
        return this.nutritionUserRepository.findAll();
    }

    public NutritionUser save(NutritionUser newNutritionUser) {
        // NEED TO ENCODE PLAIN PASSWORD BEFORE SAVING TO DB TODO
        return this.nutritionUserRepository.save(newNutritionUser);
    }
}
