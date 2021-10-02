package com.nuitblanche.triple.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByName(String name);

    Optional<User> findByName(String name);
}
