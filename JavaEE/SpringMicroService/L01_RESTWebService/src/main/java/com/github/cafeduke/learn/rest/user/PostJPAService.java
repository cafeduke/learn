package com.github.cafeduke.learn.rest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Post service that extends the JpaRepository for @Entity Post with private key of type Integer 
 *
 */
@Repository
public interface PostJPAService extends JpaRepository<Post, Integer>
{

}
