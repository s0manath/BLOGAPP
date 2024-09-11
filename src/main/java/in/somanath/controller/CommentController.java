package in.somanath.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.somanath.entity.Comment;
import in.somanath.entity.Post;
import in.somanath.service.CommentService;
import in.somanath.service.PostService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private HttpSession session;
	
	 @PostMapping("/comments")
	    public String submitComment(@RequestParam("name") String name,
	                                @RequestParam("email") String email,
	                                @RequestParam("comment") String commentContent,
	                                @RequestParam("postId") Integer postId) { // Assuming postId is passed via a hidden input

	        // Retrieve the post by postId
	        Post post = postService.getPostById(postId);

	        // Create a new Comment object and populate it with the form data
	        Comment comment = new Comment();
	        comment.setName(name);
	        comment.setEmail(email);
	        comment.setContent(commentContent);
	        comment.setPost(post); // Associate the comment with the post

	        // Save the comment
	        commentService.saveComment(comment);

	        return "redirect:/posts/" + postId; // Redirect back to the post details page
	    }
	 
	 
	 //get all the comments based on user
	 
	   @GetMapping("/commentsbyuserid")
	    public String getCommentsByUserId(Model model) {
	        // Get userId from the session
	        Integer userId = (Integer) session.getAttribute("userId");
	        
	        // Fetch comments based on the user's posts
	        List<Comment> comments = commentService.findCommentsByUserId(userId);
	        
	        // Add comments to the model to be used in the view
	        model.addAttribute("comments", comments);
	        
	        // Return the Thymeleaf template "comment.html"
	        return "comment";
	    }
	   @GetMapping("/comments/deleteComment/{commentId}")
	   public String deleteComment(@PathVariable Integer commentId) {
	       // Delete the comment by ID
	       commentService.deleteCommentById(commentId);
	       
	       // Redirect back to the comment page after deletion
	       return "redirect:/commentsbyuserid";
	   }


}
