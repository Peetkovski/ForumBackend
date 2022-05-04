package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.CommentsRepository;
import com.example.nukatokenajwt.dao.PostRepository;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Comments;
import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.service.Exceptions.HasForbiddenWordException;
import com.example.nukatokenajwt.service.Exceptions.PostDoesNotExistsException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CommentsService {

    private final UserDao userDao;
    private final CommentsRepository commentsRepository;
    private final PostRepository postRepository;

	public CommentsService(final UserDao userDao, final CommentsRepository commentsRepository, final PostRepository postRepository) {
		this.userDao = userDao;
		this.commentsRepository = commentsRepository;
		this.postRepository = postRepository;
	}

	public List<Comments> getCommentsFromPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostDoesNotExistsException(
                "Post does not exists"
        ));
        return commentsRepository.findByPost(post);
    }


    public Comments addComment( Long id,Comments comments, String username)  {
        User user = userDao.findById(username).get();

        comments.setComment(comments.getComment());
        comments.setVoteCount(0);
        comments.setPost(postRepository.getById(id));
        comments.setUser(user);
        Boolean exists = postRepository.selectExistsId(id);
        if(!exists){
            throw new PostDoesNotExistsException(
                    "Post Does not exists!"
            );
        }
        checkForbiddenWords(comments.getComment(), username);

       return commentsRepository.save(comments);

    }

    public List<Comments> findCommentsForUser(User user){

        return commentsRepository.findCommentsByUser(user);
    }


    private void checkForbiddenWords(String s,  String username){
        String[] forbidden = {"Shit"};
        List<String> forbiddenWords = Arrays.asList(forbidden);

        for (int i=0;i<forbiddenWords.size();i++){

            if(s.contains(forbiddenWords.get(i))){
                log.warn("{} Commented forbidden word", username);
                throw new HasForbiddenWordException(
                        "Comment has forbbiden word!"
                );
            }
        }


    }
}
