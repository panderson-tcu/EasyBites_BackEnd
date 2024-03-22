package edu.tcu.cs.easybites.appuser;

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
}
