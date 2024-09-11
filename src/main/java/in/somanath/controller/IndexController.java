package in.somanath.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.somanath.entity.Comment;
import in.somanath.entity.Post;
import in.somanath.service.PostService;

@Controller
public class IndexController {
	
	@Autowired
	private PostService postService;

	 @GetMapping("/")
	    public String index(Model model) {
	        List<Post> posts = postService.findAllPosts();
	        model.addAttribute("posts", posts);
	        return "index"; // Name of the HTML template
	    }
	 @GetMapping("/posts/{postId}")
	    public String getPostDetails(@PathVariable("postId") Integer postId, Model model) {
	        Post post = postService.getPostById(postId);
	        List<Comment> comments= postService.getCommentsById(postId);
	        model.addAttribute("comments", comments);
	        model.addAttribute("post", post);
	        return "PostDetails";  // The name of the Thymeleaf template for the detailed view
	    }
}
