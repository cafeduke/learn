package com.github.cafeduke.todo.mapper;

import org.springframework.stereotype.Component;

import com.github.cafeduke.todo.dto.TodoItemDto;
import com.github.cafeduke.todo.entity.TodoItem;

@Component
public class TodoItemMapper implements Mapper<TodoItem, TodoItemDto>
{
  @Override
  public TodoItem toEntity(TodoItemDto dto)
  {
    return new TodoItem(dto.id(), dto.title(), dto.completed());
  }

  @Override
  public TodoItemDto toDto(TodoItem entity)
  {
    return new TodoItemDto(entity.getId(), entity.getTitle(), entity.isCompleted());
  }
}
