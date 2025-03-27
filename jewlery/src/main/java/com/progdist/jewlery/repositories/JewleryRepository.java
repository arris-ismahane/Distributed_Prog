package com.progdist.jewlery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progdist.jewlery.model.Jewlery;

@Repository
public interface JewleryRepository extends JpaRepository<Jewlery, Long>{
    
}
