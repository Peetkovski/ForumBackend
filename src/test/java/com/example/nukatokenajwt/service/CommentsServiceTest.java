package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.CommentsRepository;
import com.example.nukatokenajwt.dao.PostRepository;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Comments;
import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.service.Exceptions.PostDoesNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentsServiceTest {


    CommentsService commentsService;


    @Mock
    UserDao userDao;
    @Mock
    PostRepository postRepository;
    @Mock
    CommentsRepository commentsRepository;


    @BeforeEach
    void setUp() {
        commentsService = new CommentsService(userDao, commentsRepository, postRepository);
    }

    @Test
    void getCommentsFromPost() {
        Post post = new Post();
        postRepository.save(post);
        User user = new User();
        user.setUserName("Tom");
        userDao.save(user);
        Comments comments = new Comments();
        comments.setComment("comment");
        comments.setUser(user);
        commentsRepository.save(comments);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));


        commentsService.getCommentsFromPost(1L);
    }

    @Test
    void notGetCommentsFromPost(){
        Post post = new Post();
        postRepository.save(post);
        User user = new User();
        user.setUserName("Tom");
        userDao.save(user);
        Comments comments = new Comments();
        comments.setComment("comment");
        comments.setUser(user);
        commentsRepository.save(comments);


        assertThatThrownBy(() -> commentsService.getCommentsFromPost(3L))
                .isInstanceOf(PostDoesNotExistsException.class)
                .hasMessageContaining("Post does not exists");

    }

    @Test
    void addComment() {
        String username = "Tom";
        Long id = 1L;

        Post post = new Post();
        postRepository.save(post);
        User user = new User();
        user.setUserName("Tom");
        userDao.save(user);
        Comments comments = new Comments();
        comments.setComment("comment");
        comments.setUser(user);
        commentsRepository.save(comments);

        when(userDao.findById(username)).thenReturn(Optional.of(user));
        when(postRepository.selectExistsId(id)).thenReturn(true);

        commentsService.addComment(id, comments, user.getUserName());


    }

    @Test
    void notAddCommentThrowPostDoesNotExists(){
        String username = "Tom";
        Long id = 1L;

        Post post = new Post();
        postRepository.save(post);
        User user = new User();
        user.setUserName("Tom");
        userDao.save(user);
        Comments comments = new Comments();
        comments.setComment("comment");
        comments.setUser(user);
        commentsRepository.save(comments);

        when(userDao.findById(username)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> commentsService.addComment(id, comments, username))
                .isInstanceOf(PostDoesNotExistsException.class)
                .hasMessageContaining("Post Does not exists!");
    }

    @Test
    void notAddCommentThrowHasForbiddenWordException() {

    }

    @Test
    void findCommentsForUser() {
        String username = "Tom";
        Long id = 1L;

        Post post = new Post();
        postRepository.save(post);
        User user = new User();
        user.setUserName("Tom");
        userDao.save(user);
        Comments comments = new Comments();
        comments.setComment("comment");
        comments.setUser(user);
        commentsRepository.save(comments);

        when(commentsRepository.findCommentsByUser(user)).thenReturn((List<Comments>) comments);
        commentsService.findCommentsForUser(user);

    }
}
