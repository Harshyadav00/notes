package com.harsh.inspireBlog.Service;

import com.harsh.inspireBlog.Exception.BlogNotFoundException;
import com.harsh.inspireBlog.Model.Blog;

import java.util.List;

public interface BlogService {

    Blog saveBlog(Blog blog);

    Blog updateBlog(Blog blog);

    Blog getBlogById(Integer id);

    List<Blog> getBlogsByAuthorId(String author);

    void deleteBlog(Integer blogId) throws BlogNotFoundException;
}
