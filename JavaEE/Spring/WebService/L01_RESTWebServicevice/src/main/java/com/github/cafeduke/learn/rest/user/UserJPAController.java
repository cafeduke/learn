/**
 * A controller exposes REST endpoints
 * A controller 
 */
package com.github.cafeduke.learn.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import java.util.function.Supplier;

@RestController
public class UserJPAController
{
  
  @Autowired
  private UserJPAService userService;

  @Autowired
  private PostJPAService postService;

  /**
   * Retrieve all users
   * 
   * @return A collection of User objects.
   */
  @GetMapping(path="/jpa/users")
  public Collection<UserEntity> getAllUsers()
  {
    return userService.findAll();
  }
  
  @GetMapping(path="/jpa/users/{id}")
  public EntityModel<UserEntity> getUserById(@PathVariable int id) throws NoSuchMethodException, SecurityException
  {
    // @PathVariable annotation says -- Convert the {id} segment to parameter id
    UserEntity user = userService.findById(id).orElseThrow(userNotFound(id));

    /**
     * HATEOAS -- Give metadata related to a resource
     * For eg: The link having all users shall be http://<host>:<port>/users
     * 
     * We do not want to hard code links. We retrieve the link attached to the controller function.
     * The controller link is built using WebMvcLinkBuilder
     * 
     * Tutorial way of creating:
     * 
     * Collection<User> listUser = ControllerLinkBuilder.methodOn(getClass()).getAllUsers();
     * Link link = ControllerLinkBuilder.linkTo(listUser).withRel("all-users");
     * 
     * The above does not hard code the method name as string. However, we are invoking the function instead of
     * pointing to it (Method object)??
     */
    EntityModel<UserEntity> resource = EntityModel.of(user);
    Link link = WebMvcLinkBuilder.linkTo(getClass(), getClass().getMethod("getAllUsers")).withRel("all-users");
    resource.add(link);

    return resource;
  }

  /**
   * Delete the User given the {@code id}
   * 
   * @param id ID of the user to be deleted
   * @return A (204 NO CONTENT) response.
   */
  @DeleteMapping(path="/jpa/users/{id}")
  public void deleteUserById(@PathVariable int id)
  {
    userService.deleteById(id);
  }

  /**
   * A new user is created by sending the JSON user object as post body to URI "/users"
   * - @PostMapping annotation -- The path "/users" is invoked using POST HTTP method.
   * - @RequestBody annotation -- Convert the request body to User object.
   * 
   * @param user JSON representation of the User object.
   * @return
   */
  @PostMapping(path="/jpa/users")
  public ResponseEntity<Object> createUser(@RequestBody UserEntity user)
  {
    UserEntity newUser = userService.save(user);

    /*
     * When an object is created, we need to
     * - Return the '201 CREATED' response code
     * - Send the URI of the newly created resource
     */

    URI location = ServletUriComponentsBuilder.fromCurrentRequest() // Get the URI from the request instead of hard-coding.
      .path("/{id}")                                                 // To the URI obtained above, append path with place holders
      .buildAndExpand(newUser.getId())
      .toUri();

    /*
     * ResponseEntity.created takes a URI location
     * - The response code shall be 201 Created
     * - The response header 'Location' shall have the URL to the newly created object.
     */
    return ResponseEntity.created(location).build();  
  }
  
  @GetMapping(path="/jpa/users/{id}/posts")
  public List<PostEntity> getPosts (@PathVariable int id)
  {
    UserEntity user = userService.findById(id).orElseThrow(userNotFound(id));
    return user.getPosts();
  }
  
  private Supplier<RuntimeException> userNotFound (int id)
  {
    return () -> new DukeResourceNotFoundException(String.format("User with ID=%d not found.", id));
  }
  
  @PostMapping(path="/jpa/users/{id}/posts")
  public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody PostEntity post)
  {
    UserEntity user = userService.findById(id).orElseThrow(userNotFound(id));
    post.setUser(user);
    PostEntity newPost = postService.save(post);
    
    URI location = ServletUriComponentsBuilder.fromCurrentContextPath() // Get the URI from the request instead of hard-coding.
        .path("/jpa/posts/{id}")                                         // To the URI obtained above, append path with place holders
        .buildAndExpand(newPost.getId())
        .toUri();    
    
    return ResponseEntity.created(location).build();
  }  
  
  @GetMapping(path="/jpa/posts/{id}")
  public EntityModel<PostEntity> getPostById (@PathVariable int id)
  {
    PostEntity post = postService.findById(id).orElseThrow(postNotFound(id));
    return EntityModel.of(post);
  }  
  
  private Supplier<RuntimeException> postNotFound (int id)
  {
    return () -> new DukeResourceNotFoundException(String.format("Post with ID=%d not found.", id));
  }  
}
