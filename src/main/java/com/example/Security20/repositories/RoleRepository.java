package com.example.Security20.repositories;

import com.example.Security20.entities.Role;
import com.example.Security20.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
