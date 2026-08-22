package com.github.cafeduke.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.cafeduke.todo.dto.TodoItemDto;
import com.github.cafeduke.todo.mapper.TodoItemMapper;
import com.github.cafeduke.todo.repository.TodoItemRepository;

import lombok.RequiredArgsConstructor;

/**
 * @RequiredArgsConstructor performs DI on final instance variables
 */
@Service
@RequiredArgsConstructor
public class TodoItemService {

  private final TodoItemRepository repository;

  private final TodoItemMapper mapper;

  public Page<TodoItemDto> getTodoItems(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    return this.repository
        .findAll(pageable)
        .map(entity -> mapper.toDto(entity));
  }

  public Page<TodoItemDto> getTodoItems(int pageNumber, int pageSize, String sortField, String sortDirection) {
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
        : Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return this.repository
        .findAll(pageable)
        .map(entity -> mapper.toDto(entity));
  }
}
