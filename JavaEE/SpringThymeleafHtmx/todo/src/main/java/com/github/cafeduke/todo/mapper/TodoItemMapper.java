package com.github.cafeduke.todo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.github.cafeduke.todo.dto.TodoItemDto;
import com.github.cafeduke.todo.entity.TodoItem;

// MapStruct automatically maps identical field names
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoItemMapper {
  TodoItemDto toDto(TodoItem entity);

  TodoItem toEntity(TodoItemDto dto);
}
