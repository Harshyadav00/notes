package com.harsh.inspireBlog.Controller;

import com.harsh.inspireBlog.Model.Blog;
import com.harsh.inspireBlog.Service.BlogService;
import com.harsh.inspireBlog.Service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class BlogController {

    @Autowired
    private BlogService blogServiceImpl ;

    @PostMapping("/new")
    public Blog saveBlog(@RequestBody Blog blog){
        return blogServiceImpl.saveBlog(blog);
    }

    @PostMapping("/update")
    public Blog updateBlog(@RequestBody Blog blog){
        return blogServiceImpl.updateBlog(blog);
    }

    @GetMapping("/getBlogById")
    public Blog getBlogById(@RequestParam Integer id){
        return blogServiceImpl.getBlogById(id);
    }

    @GetMapping("/getAllNotesByAuthor")
    public List<Blog> getAllBlogsByAuthorId(@RequestParam String author){
        return blogServiceImpl.getBlogsByAuthorId(author);
    }

    @PostMapping("/delete")
    public  void deleteBlog(@RequestParam Integer blogId){
        blogServiceImpl.deleteBlog(blogId);
    }

}
