package com.github.cafeduke.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.cafeduke.todo.entity.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>
{

  @Query("SELECT COUNT(*) FROM TodoItem t WHERE " +
    "(:completed IS NULL OR t.completed = :completed) AND " +
    "(:title IS NULL OR t.title LIKE %:title%)")
  public long count(@Param("completed") Boolean completed, @Param("title") String title);

  @Query("SELECT t FROM TodoItem t WHERE " +
    "(:completed IS NULL OR t.completed = :completed) AND " +
    "(:title IS NULL OR t.title LIKE %:title%)")
  public Page<TodoItem> filter(@Param("completed") Boolean completed, @Param("title") String title, Pageable pageable);
}
