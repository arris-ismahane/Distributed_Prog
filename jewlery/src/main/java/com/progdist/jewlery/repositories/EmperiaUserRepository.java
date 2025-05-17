package com.progdist.jewlery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progdist.jewlery.model.EmperiaUser;

@Repository
public interface EmperiaUserRepository extends JpaRepository<EmperiaUser, Long>{
    public EmperiaUser findByUsername(String username);       
}
