package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.PostRepository;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final UserDao userDao;
    private final PostRepository postRepository;

    public Optional<Post> selectPost(Long id){
        Boolean exists = postRepository.selectExistsId(id);
        if(!exists){
            throw new PostNotFoundException(
              "Post not found!"
            );
        }
        return postRepository.findById(id);
    }

    public Iterable<Post> showAllPostsDesc(){
        return postRepository.findByIdDesc();
    }
    public Iterable<Post> findMostLikedPosts(){

        return postRepository.findPostByVoteCountDesc();
    }

    public List<Post> getPostsByUser(String username){
        List<Post> posts = postRepository.findPostByUser(username);
        return posts;
    }


    public Iterable<Post> SearchPostsByName(String name){
        return postRepository.searchByPostTitle(name);

    }


    public List<Post> findPostByUser(User user){

        return  postRepository.findPostByUser(user);
    }


    public Post createPost(Post post, String username){
        User user = userDao.findById(username).get();
        post.setPostId(post.getPostId());
        post.setPostTitle(post.getPostTitle());
        post.setPostUrl("localhost:8080/post/" + post.getPostId());
        post.setPostDescription(post.getPostDescription());
        post.setUser(post.getUser());
        post.setPostTitle(post.getPostTitle());
        post.setUser(user);
        post.setVoteCount(0);
        return postRepository.save(post);
    }

    public Post giveALike(Long id){
        Post post = postRepository.getById(id);
        post.setVoteCount(post.getVoteCount()+1);
       return postRepository.save(post);
    }


    public Post commentPost(Long id){
        Post post = postRepository.getById(id);
        return post;
    }

    public Post giveADisLike(Long id){
        Post post = postRepository.getById(id);
        post.setVoteCount(post.getVoteCount()-1);
        return postRepository.save(post);
    }

    public  Iterable<Post> showPostsByCategory(String category){
        return postRepository.findPostByCategory(category);
    }


}
