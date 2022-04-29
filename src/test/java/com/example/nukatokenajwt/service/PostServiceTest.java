package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.PostRepository;
import com.example.nukatokenajwt.dao.UserDao;
import com.example.nukatokenajwt.entity.Post;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.service.Exceptions.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {


    private static PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //without this you will get NPE
        postService = new PostService(userDao, postRepository);
    }

    @Test
    void selectPost() {
        Post post = new Post();
        post.setPostId(1L);
        postRepository.save(post);
        Long id = 1L;
        boolean isFound = true;

        when(postRepository.selectExistsId(id)).thenReturn(isFound);
        
        postService.selectPost(id);

    }

    @Test
    void throwPostNotFoundException(){
    Long id = 1L;
    assertThatThrownBy(() -> postService.selectPost(id))
            .isInstanceOf(PostNotFoundException.class)
            .hasMessageContaining("Post not found!");
    }

    @Test
    void showAllPostsDesc() {
        Iterable<Post> post = postRepository.findByIdDesc();
        Iterable<Post> post1 = postService.showAllPostsDesc();
        assertEquals(post, post1);

    }

    @Test
    void findMostLikedPosts() {
        Iterable<Post> postsRepo = postRepository.findPostByVoteCountDesc();
        Iterable<Post> postsService = postService.findMostLikedPosts();

        assertEquals(postsRepo, postsService);
    }

    @Test
    void getPostsByUser() {
        String username = "raj123";
        List<Post> userPostsRepo = postRepository.findPostByUser(username);
        List<Post> userPostsService = postService.getPostsByUser(username);
        assertEquals(userPostsRepo, userPostsService);
    }

    @Test
    void searchPostsByName() {
        String title = "java";
        Iterable<Post> searchPostRepo = postRepository.searchByPostTitle(title);
        Iterable<Post> searchPostService = postService.SearchPostsByName(title);
        assertEquals(searchPostRepo, searchPostService);
    }

    @Test
    void findPostByUser() {
        User user = new User();
        List<Post> findPostByUserRepo = postRepository.findPostByUser(user);
        List<Post> findPostByUserService = postService.findPostByUser(user);
        assertEquals(findPostByUserRepo, findPostByUserService);
    }

    @Test
    void updatePost() {
        User user = new User();
        user.setUserName("raj123");
        Post post = new Post();
        post.setPostId(1L);
        postRepository.save(post);
        Long postId = 1L;
        String username = "raj123";
        Post post1 = postRepository.findPostByPostId(postId);
        User user1 = userDao.findUserByUserName(username);

            post1.setPostTitle("nazwa");
            post1.setPostDescription("Opis");
            postRepository.save(post1);

    }

    @Test
    void deletePost() {
        User user = new User();
        user.setUserName("apweorng");
        user.setUserPassword("wetwea");
        userDao.save(user);
        Long postId = 1L;
        Post post = new Post();
        post.setPostId(1L);
        post.setPostTitle("Java");
        post.setPostDescription("Java problemy");
        post.setUser(user);
        user.setPost(post);
        userDao.save(user);
        postRepository.save(post);
            postService.deletePost(post.getPostId(), "Tom");
    }

    @Test
    void throwNotUserPostFound() {
        Long postId = 1L;
        Post post = new Post();
        post.setPostId(1L);
        post.setPostTitle("Java");
        post.setPostDescription("Java problemy");
        String username = "raj123";
        assertThatThrownBy(() -> postService.deletePost(postId, username))
                .isInstanceOf(NotUserPostFound.class)
                .hasMessageContaining("Post does not belongs to user!");
    }

    @Test
    void createPost() {
        User user = new User();
        user.setUserName("Piotr");
        user.setUserPassword("12345");
        userDao.save(user);
        Post post = new Post();
        post.setPostTitle("Java");
        post.setPostDescription("Problemy");

        when(userDao.findById(user.getUserName())).thenReturn(Optional.of(user));
        postService.createPost(post, user.getUserName());

        ArgumentCaptor<Post> argumentCaptor =
                ArgumentCaptor.forClass(Post.class);

        verify(postRepository)
                .save(argumentCaptor.capture());

        Post postCaptured = argumentCaptor.getValue();

        assertEquals(postCaptured, post);
    }

    @Test
    void giveALike() {


        User user = new User();
        user.setUserName("piotr");
        user.setUserPassword("Tom");
        userDao.save(user);
        Post post = new Post();
        post.setPostId(1L);
        post.setVoteCount(0);
        post.setUser(user);
        postRepository.save(post);

        when(postRepository.getById(post.getPostId())).thenReturn(post);

        postService.giveALike(post.getPostId());

    }

    @Test
    void commentPost() {
        Post post = new Post();
    }

    @Test
    void giveADisLike() {
        Post post = new Post();
        post.setPostId(1L);
        post.setVoteCount(0);
        postRepository.save(post);

        when(postRepository.getById(post.getPostId())).thenReturn(post);

        postService.giveADisLike(post.getPostId());

    }

    @Test
    void showPostsByCategory() {
        String category = "Anime";
        Iterable<Post> postsRepo = postRepository.findPostByCategory(category);
        Iterable<Post> postsService = postService.showPostsByCategory(category);
        assertEquals(postsRepo, postsService);
    }



}
