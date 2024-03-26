package edu.tcu.cs.easybites.appuser;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser save(AppUser newAppUser) {
        return this.appUserRepository.save(newAppUser);
    }

    public List<AppUser> findAll() {
        return this.appUserRepository.findAll();
    }

    public AppUser findById(String appUserId) {
        return this.appUserRepository.findByUserId(appUserId)
                .orElseThrow(() -> new ObjectNotFoundException("app user", appUserId));
    }

    public List<Recipe> findLikedRecipes(String appUserId) {
        AppUser foundAppUser = this.findById(appUserId);
        return foundAppUser.getRecipes();
    }

    public List<Recipe> findShoppingCartRecipes(String appUserId) {
        AppUser foundAppUser = this.findById(appUserId);
        return foundAppUser.getShoppingCart();
    }

    public AppUser setUserAllergens(String appUserId, List<Allergen> newAllergens) {
        return this.appUserRepository.findByUserId(appUserId)
                .map(oldAppUser -> {
                    oldAppUser.setAllergens(newAllergens);
                    return this.appUserRepository.save(oldAppUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", appUserId));
    }

    public AppUser setUserAppliances(String appUserId, List<Appliance> newAppliances) {
        return this.appUserRepository.findByUserId(appUserId)
                .map(oldAppUser -> {
                    oldAppUser.setAppliances(newAppliances);
                    return this.appUserRepository.save(oldAppUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", appUserId));
    }
}
















