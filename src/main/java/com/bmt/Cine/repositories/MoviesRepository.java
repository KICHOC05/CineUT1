package com.bmt.Cine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmt.Cine.models.Movies;

public interface MoviesRepository extends JpaRepository<Movies, Integer>{

}
