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

}
