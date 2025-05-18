package com.progdist.jewlery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progdist.jewlery.model.Jewlery;

@Repository
public interface JewleryRepository extends JpaRepository<Jewlery, Long>{

    List<Jewlery> findByUser_Id(Long userId);

    List<Jewlery> findByUser_IdNot(Long userId);

    List<Jewlery> findByUser_IdAndCategory_Id(Long userId, Long categoryId);

    List<Jewlery> findByUser_IdNotAndCategory_Id(Long userId, Long categoryId);

}
