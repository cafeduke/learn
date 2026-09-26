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

  /**
   * Read
   * ----
   */

  public TodoItemDto findById(Long id)
  {
    return repository.findById(id)
      .map(entity -> mapper.toDto(entity))
      .orElseThrow(() -> new TodoItemNotFoundException(id));
  }

  public Page<TodoItemDto> findAll(int pageIndex, int pageSize)
  {
    return findAll(pageIndex, pageSize, null, null);
  }

  public Page<TodoItemDto> findAll(int pageIndex, int pageSize, String sortField, String sortDirection)
  {
    Pageable pageable = getPageable(pageIndex, pageSize, sortField, sortDirection);
    return this.repository
      .findAll(pageable)
      .map(entity -> mapper.toDto(entity));
  }

  /**
   * @return Returns the total number of entities available.
   */
  public long count()
  {
    return repository.count();
  }

  public long count(Boolean compeleted, String title)
  {
    return repository.count(compeleted, title);
  }

  public Page<TodoItemDto> filter(Boolean compeleted, String title, int pageIndex, int pageSize)
  {
    return filter(compeleted, title, pageIndex, pageSize, null, null);
  }

  public Page<TodoItemDto> filter(Boolean compeleted, String title, int pageIndex, int pageSize, String sortField, String sortDirection)
  {
    Pageable pageable = getPageable(pageIndex, pageSize, sortField, sortDirection);

    return this.repository
      .filter(compeleted, title, pageable)
      .map(mapper::toDto);
  }

  private Pageable getPageable(int pageIndex, int pageSize, String sortField, String sortDirection)
  {
    Sort sort = null;
    if (sortField != null && sortDirection != null)
    {
      Sort.Direction order = Sort.Direction.valueOf(sortDirection);
      sort = (order == Sort.Direction.ASC) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }
    return (sort == null) ? PageRequest.of(pageIndex, pageSize) : PageRequest.of(pageIndex, pageSize, sort);
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
