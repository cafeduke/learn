package com.github.cafeduke.todo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.cafeduke.todo.dto.TodoItemDto;
import com.github.cafeduke.todo.entity.TodoItem;
import com.github.cafeduke.todo.exception.TodoItemNotFoundException;
import com.github.cafeduke.todo.mapper.TodoItemMapper;
import com.github.cafeduke.todo.repository.TodoItemRepository;

import lombok.RequiredArgsConstructor;

/**
 * @RequiredArgsConstructor performs DI on final instance variables
 */
@Service
@RequiredArgsConstructor
public class TodoItemService
{

  private final TodoItemRepository repository;

  private final TodoItemMapper mapper;

  /*
   * Read
   * ----
   */

  public TodoItemDto findById(Long id)
  {
    return repository.findById(id)
      .map(entity -> mapper.toDto(entity))
      .orElseThrow(() -> new TodoItemNotFoundException(id));
  }

  public List<TodoItemDto> findAll()
  {
    return repository.findAll()
      .stream()
      .map(mapper::toDto)
      .toList();
  }

  /**
   * @return Returns the number of entities available.
   */
  public long count()
  {
    return repository.count();
  }

  public long countByCompleted(boolean completed)
  {
    return repository.countByCompleted(completed);
  }

  public Page<TodoItemDto> findAll(int pageIndex, int pageSize)
  {
    Pageable pageable = PageRequest.of(pageIndex, pageSize);
    return this.repository
      .findAll(pageable)
      .map(mapper::toDto);
  }

  public Page<TodoItemDto> findByCompleted(boolean completed, int pageIndex, int pageSize)
  {
    Pageable pageable = PageRequest.of(pageIndex, pageSize);
    return this.repository
      .findByCompleted(completed, pageable)
      .map(mapper::toDto);
  }

  public Page<TodoItemDto> findAll(int pageIndex, int pageSize, String sortField, String sortDirection)
  {
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
    return this.repository
      .findAll(pageable)
      .map(entity -> mapper.toDto(entity));
  }

  /**
   * Update
   * ------
   */

  public void save(TodoItemDto dto)
  {
    repository.save(mapper.toEntity(dto));
  }

  public TodoItemDto toggleCompletedStatus(Long id)
  {
    TodoItem item = repository.findById(id)
      .orElseThrow(() -> new TodoItemNotFoundException(id));

    item.toggleStatus();
    TodoItem entity = repository.save(item);
    return mapper.toDto(entity);
  }

  public void toggleCompletedStatus()
  {
    List<TodoItem> items = repository.findAll();
    items.forEach(TodoItem::toggleStatus);
    repository.saveAll(items);
  }

  /**
   * Delete
   * ------
   */

  public void deleteById(Long id)
  {
    repository.deleteById(id);
  }
}
