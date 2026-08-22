/**
 * Service 
 *  A service is annotated with @Component
 *  A service exposes APIs, interacts with the back-end and retrieves data.
 *  A service object is injected into a controller.  
 */
package com.github.cafeduke.learn.rest.user;

import org.springframework.stereotype.Component;
import java.util.*;
/**
 * A service exposes operations on an entity.  
 * In this case UserService exposes opeartions like findById, save, deleteById on User entity. 
 */
@Component
public class UserService
{
   private static Map<Integer,User> mapIdUser = new Hashtable<>();
   
   static
   {
      User user[] = new User[] {
        new User(1, "Raghu", new Date()),
        new User(2, "Madhu", new Date()),
        new User(3, "Pavi", new Date())
      };
      
      for (User currUser : user)
         mapIdUser.put(currUser.getId(), currUser);
   }
   
   public Collection<User> findAll()
   {
      return mapIdUser.values();
   }
   
   /**
    * Find the User by the given ID.
    * 
    * @param id ID of the user
    * @return Returns the User object or null if user with {@code id} not found.
    */
   public User findById(int id)
   {
      return mapIdUser.get(id);
   }
   
   /**
    * Delete the User with the given ID.
    * 
    * @param id ID of the user
    * @return Returns the deleted User object or null if user with {@code id} not found.
    */
   public User deleteById (int id)
   {
      return mapIdUser.remove(id);
   }
   
   /**
    * Update an existing user object (with same ID as {@code user}) or add {@code user} object.  
    *  
    * @param user The user object to be either updated for added.
    * @return The user object post updation or addition.
    */
   public User save(User user)
   {
      if (user.getId() == null || user.getId() == 0)
         user.setId(mapIdUser.size()+1);
      mapIdUser.put(user.getId(), user);
      return user;
   }
   
}
