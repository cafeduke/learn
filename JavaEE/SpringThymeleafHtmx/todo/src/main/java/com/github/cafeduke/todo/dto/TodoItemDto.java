package com.github.cafeduke.todo.dto;

import jakarta.validation.constraints.NotEmpty;

// Note: The id is Long (object rather than primitive long) and can be null during form submissions

public record TodoItemDto(Long id, @NotEmpty(message = "Title is mandatory") String title, boolean completed)
{
  public static TodoItemDto empty()
  {
    return new TodoItemDto(null, "", false);
  }
}
