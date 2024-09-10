package in.somanath.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.somanath.binding.DashbordResponse;
import in.somanath.binding.PostForm;
import in.somanath.entity.Post;

@Service
public interface PostService {

	boolean addingNewPost(PostForm postForm, Object object);

	DashbordResponse getDashboardData(Integer userId);

	PostForm getPostById(Integer postId, Integer userId);

	boolean updatePost(PostForm postForm, Integer userId);

	boolean deletePostById(Integer postId, Integer userId);

	List<Post> findAllPosts();

	Post getPostById(Integer postId);
	
	List<Post> findAllPostsId(Integer userId);

	
	

}
