package com.harsh.inspireBlog.Repository;

import com.harsh.inspireBlog.Model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    Optional<List<Blog>> findAllByAuthor(String author);

}
