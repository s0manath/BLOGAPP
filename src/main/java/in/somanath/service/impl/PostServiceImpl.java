package in.somanath.service.impl;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.somanath.binding.DashbordResponse;
import in.somanath.binding.PostForm;
import in.somanath.entity.Comment;
import in.somanath.entity.Post;
import in.somanath.entity.User;
import in.somanath.repo.CommentRepository;
import in.somanath.repo.PostRepository;
import in.somanath.repo.UserRepository;
import in.somanath.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);


    @Override
    public boolean addingNewPost(PostForm postForm, Object object) {
        if (postForm.getTitle() != null && postForm.getContent() != null && postForm.getDescription() != null) {

            Post post = new Post();
            post.setTitle(postForm.getTitle());
            post.setContent(postForm.getContent());
            post.setDescription(postForm.getDescription());

            // Get user ID from the object (session attribute)
            if (object != null) {
                int userId = Integer.parseInt(object.toString());

                // Fetch the user by ID
                Optional<User> userObject = userRepository.findById(userId);
                if (userObject.isPresent()) {
                    post.setUser(userObject.get());
                }
            }

            postRepository.save(post);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DashbordResponse getDashboardData(Integer userId) {
        DashbordResponse response = new DashbordResponse();

        // Get the user by ID
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Fetch posts by user
            List<Post> posts = postRepository.findByUser(user);
            response.setUser(user);
            response.setPosts(posts);
        }

        return response;
    }

    @Override
    public PostForm getPostById(Integer postId, Integer userId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent() && postOpt.get().getUser().getUserId().equals(userId)) {
            Post post = postOpt.get();
            PostForm postForm = new PostForm();
            postForm.setPostId(post.getPostId());
            postForm.setTitle(post.getTitle());
            postForm.setContent(post.getContent());
            postForm.setDescription(post.getDescription());
            return postForm;
        }
        return null;
    }

    // Update an existing post
    @Override
    public boolean updatePost(PostForm postForm, Integer userId) {
        Optional<Post> postOpt = postRepository.findById(postForm.getPostId());
        if (postOpt.isPresent() && postOpt.get().getUser().getUserId().equals(userId)) {
            Post post = postOpt.get();
            post.setTitle(postForm.getTitle());
            post.setContent(postForm.getContent());
            post.setDescription(postForm.getDescription());
            postRepository.save(post);
            return true;
        }
        return false;
    }

    // Delete a post by its ID
    @Override
    public boolean deletePostById(Integer postId, Integer userId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent() && postOpt.get().getUser().getUserId().equals(userId)) {
            postRepository.delete(postOpt.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        logger.debug("Fetching all posts from the database.");
        List<Post> listOfPost = postRepository.findAll();
        logger.debug("Number of posts retrieved: {}", listOfPost.size());
        return listOfPost;
    }

    @Override
    public Post getPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Override
    public List<Comment> getCommentsById(Integer postId) {
        return commentRepository.findByPostPostId(postId);
    }
}
