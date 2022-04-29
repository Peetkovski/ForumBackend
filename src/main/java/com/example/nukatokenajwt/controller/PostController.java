package com.example.nukatokenajwt.controller;

import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/posts")
@PreAuthorize("hasRole('User')")
public class PostController {

    private final PostService postService;
    private final UserDao userDao;

    @PostMapping("/add")
    public void addPost(@RequestBody Post post, Principal principal){

       String username = principal.getName();

        postService.createPost(post, username);
    }
    @GetMapping("/findBy/user/{username}")
    public List<Post> findByPostsByUsername(@PathVariable("username")String username){

        User user = userDao.findUserByUserName(username);

        return postService.findPostByUser(user);
    }

    @GetMapping("/find/{name}")
    public Iterable<Post> findByName(@PathVariable("name") String name){
        return postService.SearchPostsByName(name);
    }

    @GetMapping("/trending")
    public Iterable<Post> findTrendingPosts(){

        return postService.findMostLikedPosts();
    }


    @GetMapping("/user/{username}")
    public List<Post> searchPostByUser(@PathVariable("username")String username){
        return postService.getPostsByUser(username);
    }


    @GetMapping("/all")
    public Iterable<Post> showAllPostsDesc(){

        return postService.showAllPostsDesc();
    }

    @GetMapping("/get/category/{category}")
    public Iterable<Post> showPostsByCategories(@PathVariable("category") String category){

        return postService.showPostsByCategory(category);
    }

    @GetMapping("/get/post/{id}")
    public Optional<Post> getPostById(@PathVariable("id") Long id){


        return postService.selectPost(id);
    }


    @DeleteMapping("/delete/{postId}")
    public void deletePostById(@PathVariable("postId")Long postId, Principal principal){

        String username = principal.getName();

        postService.deletePost(postId, username);
    }

    @GetMapping("/like/{id}")
    public Post likePostById(@PathVariable("id") Long id){


        return postService.giveALike(id);
    }
    @GetMapping("/dislike/{id}")
    public Post dislikePostById(@PathVariable("id") Long id){


        return postService.giveADisLike(id);
    }
}
