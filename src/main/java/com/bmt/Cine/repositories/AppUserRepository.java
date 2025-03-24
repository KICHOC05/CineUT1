package com.bmt.Cine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmt.Cine.models.AppUsers;

public interface AppUserRepository extends JpaRepository<AppUsers, Integer>{

	public AppUsers findByEmail(String email);
}