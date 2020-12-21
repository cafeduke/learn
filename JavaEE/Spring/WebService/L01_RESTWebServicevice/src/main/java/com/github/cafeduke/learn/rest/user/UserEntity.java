package com.github.cafeduke.learn.rest.user;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import io.swagger.annotations.*;

@Entity
@ApiModel(description = "The user object")
public class UserEntity
{
  @Id
  @GeneratedValue
  private Integer id;

  @ApiModelProperty(notes = "Name should have atleast 2 characters")
  @Size(min = 2, message = "Name should have atleast 2 characters")
  private String name;

  @ApiModelProperty(notes = "Birth should be in the past")
  @Past
  private Date dob;

  /**
   * OneToMany
   *   - Indicates one user can have many posts
   * mappedBy : 
   *   - This shows that posts is derived from the relationship (not part of schema)
   *   - Every post shall have the userId as the foreign key. This is used to figure out all posts of this user.
   *   - We are saying that the field called 'user', in the 'Post' entity is how 'Post' is linked to 'User'
   */
  @OneToMany(mappedBy = "user")
  private List<PostEntity> posts;

  protected UserEntity()
  {

  }

  public UserEntity(Integer id, String name, Date dob)
  {
    super();
    this.id = id;
    this.name = name;
    this.dob = dob;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Date getDob()
  {
    return dob;
  }

  public void setDob(Date dob)
  {
    this.dob = dob;
  }

  public List<PostEntity> getPosts()
  {
    return posts;
  }

  public void setPosts(List<PostEntity> posts)
  {
    this.posts = posts;
  }

  @Override
  public String toString()
  {
    return "User [id=" + id + ", name=" + name + ", dob=" + dob + "]";
  }
}
