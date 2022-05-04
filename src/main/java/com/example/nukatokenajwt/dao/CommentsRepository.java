package com.example.nukatokenajwt.dao;

import com.example.nukatokenajwt.entity.Comments;
import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("" +
            "SELECT CASE WHEN COUNT(s) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Comments s " +
            "WHERE s.post = ?1"
    )
    List<Comments> findCommentsByUser(User user);
    List<Comments> findByPost(Post post);

}
