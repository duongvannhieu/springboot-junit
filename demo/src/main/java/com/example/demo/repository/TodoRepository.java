package com.example.demo.repository;

import com.example.demo.db.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(value = "SELECT * FROM todo t WHERE t.title = :title", nativeQuery = true)
    Todo findByTitle(@Param("title") String title);
}
