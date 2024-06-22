package com.harsh.inspireBlog.Service;

import com.harsh.inspireBlog.Exception.BlogNotFoundException;
import com.harsh.inspireBlog.Model.Blog;
import com.harsh.inspireBlog.Repository.BlogRepository;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogServiceImpl implements  BlogService{
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blog) {

        String email = "";
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    email = userDetails.getUsername();
                }
            }
        } catch (Exception e) {
            logger.error("Error retrieving email from SecurityContext: ", e);
        }

        // 2. Set Author Email in Blog Object (Optional, Security-Conscious Check)
        if (StringUtils.isNotBlank(email)) {
            blog.setAuthor(email); // Assuming a field for author email
        } else {
            // Handle cases where email cannot be retrieved (e.g., anonymous user)
            logger.warn("Email could not be retrieved from SecurityContext. Author email not set.");
        }

        blog.setCreatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Blog updatedBlog) {
        String currentLoggedInEmail = "";
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    currentLoggedInEmail = userDetails.getUsername(); // Assuming username represents email
                }
            }
        } catch (Exception e) {
            logger.error("Error retrieving email from SecurityContext: ", e);
        }

        Blog existingBlog = blogRepository.findById(updatedBlog.getId()).orElse(null);
        if (existingBlog == null) {
            return null;
        }

        if (StringUtils.isNotBlank(currentLoggedInEmail) &&
                !currentLoggedInEmail.equals(existingBlog.getAuthor())) {
            return null;
        }

        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setUpdatedAt(LocalDateTime.now());

        try {
            return blogRepository.save(existingBlog);
        } catch (DataAccessException e) {
            logger.error("Error updating blog post: ", e);
            throw new RuntimeException("Error updating blog post" + e.getMessage()); // Or return a custom error response
        }
    }


    @Override
    public Blog getBlogById(Integer id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with given id: " + id));
    }

    @Override
    public List<Blog> getBlogsByAuthorId(String author) {
        return blogRepository.findAllByAuthor(author)
                .orElseThrow(() -> new BlogNotFoundException("Blogs not found with given author Email: " + author));

    }

    @Override
    public void deleteBlog(Integer blogId) throws BlogNotFoundException {

        // Check if blog exists before deleting
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + blogId));

        // Delete blog if it exists
        blogRepository.deleteById(blogId);
    }

}
