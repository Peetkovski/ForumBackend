package com.example.nukatokenajwt.dao;

import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("" +
            "SELECT CASE WHEN COUNT(s) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Post s " +
            "WHERE s.postId = ?1"
    )
    Boolean selectExistsId(Long id);


    @Query("select  f from Post as f order by f.postId desc")
    Iterable<Post> findByIdDesc();
    Iterable<Post> searchByPostTitle(String title);
    Post findPostByPostId(Long postId);

    List<Post> findPostByUser(User user);

    @Query(""+
            "SELECT f FROM Post as f ORDER BY f.voteCount DESC"
    )
    Iterable<Post> findPostByVoteCountDesc();

    List<Post> findPostByUser(String username);

    Iterable<Post> findPostByCategory(String category);

}
