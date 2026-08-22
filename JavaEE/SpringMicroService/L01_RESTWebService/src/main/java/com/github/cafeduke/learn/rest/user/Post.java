package com.github.cafeduke.learn.rest.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post
{
  @Id
  @GeneratedValue
  private Integer id;
  
  private String description;
  
  /**
   * fetch = FetchType.LAZY
   *   - We don't want user to eagerly fetch the posts and post to eagarly fetch the user.
   * ManyToOne
   *   - Many Posts can have one User.   
   */
  @JsonIgnore
  @ManyToOne(fetch=FetchType.LAZY)  
  private User user;

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }  

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  @Override
  public String toString()
  {
    // NOTE: 
    //  - Make sure not to include references like 'user'. 
    //  - If user is printing post and post is printing user it will run into infinite loop.
    return "Post [id=" + id + ", description=" + description + "]";
  }
}
