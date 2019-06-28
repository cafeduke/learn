/**
 * A controller exposes REST endpoints
 * A controller 
 */
package com.github.cafeduke.learn.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.cafeduke.learn.rest.exception.DukeResourceNotFoundException;

import java.net.URI;
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
      // @PathVariable annotation says -- Convert the {id} segment to parameter id
      User user = service.findById(id); 
      if (user == null)
         throw new DukeResourceNotFoundException(String.format("User with ID=%d not found.", id));
      return user;
   }
   
   /**
    * Delete the User given the {@code id}
    * 
    * @param id ID of the user to be deleted
    * @return A (204 NO CONTENT) response.
    */
   @DeleteMapping(path="/user/{id}")
   public ResponseEntity<Object> deleteUserById(@PathVariable int id)
   {
      User user = service.deleteById(id);
      if (user == null)
         throw new DukeResourceNotFoundException(String.format("Could not delete. User with ID=%d not found.", id));
      return ResponseEntity.noContent().build();
   }
   
   /**
    * A new user is created by sending the JSON user object as post body to URI "/user"
    *    - @PostMapping annotation -- The path "/user" is invoked using POST HTTP method.
    *    - @RequestBody annotation -- Convert the request body to User object.
    *    
    * @param user JSON representation of the User object.
    * @return 
    */
   @PostMapping(path="/user")
   public ResponseEntity<Object> createUser(@RequestBody User user)
   {
      User newUser = service.save(user);
      
      /*
       * When an object is created, we need to 
       *    - Return the '201 CREATED' response code
       *    - Send the URI of the newly created resource
       */
      
      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()            // Get the URI from the request instead of hard-coding.
         .path("{id}")                    // To the URI obtained above, append path with place holders
         .buildAndExpand(newUser.getId())
         .toUri();
      
      /*
       * ResponseEntity.created takes a URI location
       *    - The response code shall be 201 Created 
       *    - The response header 'Location' shall have the URL to the newly created object.
       */
      return ResponseEntity.created(location).build();
      
   }
}
