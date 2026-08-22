package com.github.cafeduke.learn.rest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User service that extends the JpaRepository for @Entity User with private key of type Integer 
 *
 */
@Repository
public interface UserJPAService extends JpaRepository<User, Integer>
{

}
