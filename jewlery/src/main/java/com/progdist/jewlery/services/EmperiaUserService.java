package com.progdist.jewlery.services;

import com.progdist.jewlery.excpetions.NotFoundException;
import com.progdist.jewlery.model.EmperiaUser;
import com.progdist.jewlery.model.EmperiaUserType;
import com.progdist.jewlery.model.inputs.EmperiaUserInput;
import com.progdist.jewlery.repositories.EmperiaUserRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmperiaUserService {
    private final EmperiaUserRepository repository;

    @PostConstruct
    public void createAdminIfNotExists() {
        String adminUsername = "admin";
        if (repository.findByUsername(adminUsername) == null) {
            EmperiaUser admin = EmperiaUser.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .username(adminUsername)
                    .password("admin") 
                    .birthDate(0L) 
                    .emperiaUserType(EmperiaUserType.ADMIN) 
                    .build();

            repository.save(admin);
            System.out.println("Admin user created with username: " + adminUsername);
        }
    }

    public List<EmperiaUser> getEmperiaUsers() {
        return repository.findAll();
    }

    public EmperiaUser getEmperiaUserById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Exception", "EmperiaUser not found"));
    }

    public EmperiaUser getEmperiaUserByUsername(String username) {
        var user = repository.findByUsername(username);
        if (user == null)
            throw new NotFoundException("Not Found Exception", "EmperiaUser not found");
        return user;
    }

    public void deleteEmperiaUser(long id) {
        repository.deleteById(id);
    }

    public EmperiaUser createEmperiaUser(EmperiaUserInput input) {
        if (repository.findByUsername(input.username()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        return repository.save(input.toEntity());
    }

    public EmperiaUser updateEmperiaUser(long id, EmperiaUserInput input) {
        var old_user = getEmperiaUserById(id);
        var new_user = input.toEntity();
        new_user.setCreationDate(old_user.getCreationDate());
        new_user.setId(old_user.getId());
        return repository.save(new_user);
    }
}
