package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.system.exception.ObjectNotFoundException;
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

    public NutritionUser findById(Integer nutritionUserId){
        return this.nutritionUserRepository.findById(nutritionUserId)
                .orElseThrow(() -> new ObjectNotFoundException("nutrition user", nutritionUserId));
    }

    public NutritionUser save(NutritionUser newNutritionUser) {
        // NEED TO ENCODE PLAIN PASSWORD BEFORE SAVING TO DB TODO
        return this.nutritionUserRepository.save(newNutritionUser);
    }

    public NutritionUser update(Integer nutritionUserId, NutritionUser update){
        return this.nutritionUserRepository.findById(nutritionUserId)
                .map(oldNutritionUser -> {
                    oldNutritionUser.setFirstName(update.getFirstName());
                    oldNutritionUser.setLastName(update.getLastName());
                    oldNutritionUser.setAdminLevel(update.getAdminLevel());
                    oldNutritionUser.setEmail(update.getEmail());
                    return this.nutritionUserRepository.save(oldNutritionUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("nutrition user", nutritionUserId));
    }

}
