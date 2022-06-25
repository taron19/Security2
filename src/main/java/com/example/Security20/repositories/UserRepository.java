package com.example.Security20.repositories;

import com.example.Security20.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //@Query(value = "select* from users where username=:username",nativeQuery = true)
    User findByUsername(/*@Param("username")*/ String username);
}
