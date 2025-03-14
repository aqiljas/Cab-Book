package com.mega.citycab.models;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminMyAppUserRepository extends JpaRepository<AdminMyAppUser, Long> {
    
    Optional<AdminMyAppUser> findByUserName(String userName);
}
