package com.progdist.jewlery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progdist.jewlery.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
