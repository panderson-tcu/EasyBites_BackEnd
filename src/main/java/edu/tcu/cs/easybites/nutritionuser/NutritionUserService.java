package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NutritionUserService implements UserDetailsService {

    private final NutritionUserRepository nutritionUserRepository;

    private PasswordEncoder passwordEncoder;

    public NutritionUserService(NutritionUserRepository nutritionUserRepository, PasswordEncoder passwordEncoder) {
        this.nutritionUserRepository = nutritionUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<NutritionUser> findAll(){
        return this.nutritionUserRepository.findAll();
    }

    public NutritionUser findById(Integer nutritionUserId){
        return this.nutritionUserRepository.findById(nutritionUserId)
                .orElseThrow(() -> new ObjectNotFoundException("nutrition user", nutritionUserId));
    }

    public NutritionUser save(NutritionUser newNutritionUser) {
        // NEED TO ENCODE PLAIN text PASSWORD BEFORE SAVING TO DB TODO
        newNutritionUser.setPassword(this.passwordEncoder.encode(newNutritionUser.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.nutritionUserRepository.findByEmail(username) // find user from database
                .map(nutritionUser -> new MyUserPrincipal(nutritionUser)) //if found, wrap returned user instance in MyUserPrincipal instance
                .orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not found.")); // otherwise, throw an exception
    }

    public void changeAccountStatus(String newStatus, Integer nutritionUserId) {
        this.nutritionUserRepository.findById(nutritionUserId)
                .map(oldUser -> {
                    oldUser.setEnabled(Boolean.parseBoolean(newStatus));
                    return this.nutritionUserRepository.save(oldUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("nutrition user", nutritionUserId));
    }
}
