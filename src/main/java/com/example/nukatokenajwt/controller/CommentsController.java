package com.example.nukatokenajwt.controller;

import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Comments;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.service.CommentsService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/comment")
@PreAuthorize("hasRole('User')")
public class CommentsController {

    private final CommentsService commentsService;
    private final UserDao userDao;

	public CommentsController(final CommentsService commentsService, final UserDao userDao) {
		this.commentsService = commentsService;
		this.userDao = userDao;
	}

	@PostMapping("/add/{id}")
    public Comments addComment(@PathVariable Long id, @RequestBody Comments comments, Principal principal){

        String username = principal.getName();

       return commentsService.addComment(id, comments, username);

    }

    @GetMapping("/get/findBy/user/{username}")
    public List<Comments> getCommentsForUser(@PathVariable("username")String username){
        User user = userDao.findUserByUserName(username);

        return commentsService.findCommentsForUser(user);
    }

    @GetMapping("/get/post/{id}")
    public List<Comments> getCommentsByPost(@PathVariable Long id){

        return commentsService.getCommentsFromPost(id);
    }
}
