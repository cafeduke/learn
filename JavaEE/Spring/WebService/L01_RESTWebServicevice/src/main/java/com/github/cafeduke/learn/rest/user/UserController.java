package com.github.cafeduke.learn.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class UserController
{
   @Autowired
   private UserService service;
   
   @GetMapping(path="/user")
   public Collection<User> findAllUsers()
   {
      return service.findAll();
   }
   
   @GetMapping(path="/user/{id}")
   public User getUserById(@PathVariable int id)
   {
      return service.findById(id);
   }
}
