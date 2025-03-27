package com.progdist.jewlery.services;

import com.progdist.jewlery.excpetions.NotFoundException;
import com.progdist.jewlery.model.EmperiaUser;
import com.progdist.jewlery.model.inputs.EmperiaUserInput;
import com.progdist.jewlery.repositories.EmperiaUserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmperiaUserService {
    private final EmperiaUserRepository repository;

    public List<EmperiaUser> getEmperiaUsers() {
        return repository.findAll();
    }

    public EmperiaUser getEmperiaUserById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Exception", "EmperiaUser not found"));
    }

    public void deleteEmperiaUser(long id) {
        repository.deleteById(id);
    }

    public EmperiaUser createEmperiaUser(EmperiaUserInput input) {
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
