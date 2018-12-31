package com.github.cafeduke.learn.rest.user;

import org.springframework.stereotype.Component;
import java.util.*;

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
   
   public User findById(int id)
   {
      return mapIdUser.get(id);
   }
   
   public void save(User user)
   {
      if (user.getId() == null || user.getId() == 0)
         user.setId(mapIdUser.size()+1);
      mapIdUser.put(user.getId(), user);
   }
   
}
