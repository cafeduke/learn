package com.github.cafeduke.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.cafeduke.todo.entity.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>
{
  public long countByCompleted(boolean completed);

  public Page<TodoItem> findByCompleted(boolean completed, Pageable pageable);
}
