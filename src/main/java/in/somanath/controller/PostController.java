package in.somanath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.somanath.binding.DashbordResponse;
import in.somanath.binding.PostForm;
import in.somanath.identify.Identifiers;
import in.somanath.service.PostService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PostService postService;

    @GetMapping("/dashboard")
    public String dashboardUnique(Model model) {
        Integer userId = (Integer) session.getAttribute(Identifiers.USER_ID);

        if (userId == null) {
            return Identifiers.REDIRECT_LOGIN; // Redirect to login if session is invalid
        }

        DashbordResponse dashbordData = postService.getDashboardData(userId);
        model.addAttribute("dashboardData", dashbordData);

        return Identifiers.DASHBOARD;
    }

    @GetMapping("/newpost")
    public String addPost(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "NewPost";
    }

    @PostMapping("/newpost")
    public String addPost(@ModelAttribute("postForm") PostForm postForm, Model model) {
        Integer userId = (Integer) session.getAttribute(Identifiers.USER_ID);

        if (userId == null) {
            return Identifiers.REDIRECT_LOGIN; // Redirect to login if session is invalid
        }

        boolean isPostAdded = postService.addingNewPost(postForm, userId);

        if (isPostAdded) {
            return Identifiers.REDIRECT_DASHBOARD;
        } else {
            model.addAttribute("errorMessage", "Failed to create a new post. Please try again.");
            return Identifiers.NEW_POST;
        }
    }
    
    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable("id") Integer postId, Model model) {
        Integer userId = (Integer) session.getAttribute(Identifiers.USER_ID);
        if (userId == null) {
            return Identifiers.REDIRECT_LOGIN; // Redirect to login if session is invalid
        }

        PostForm postForm = postService.getPostById(postId, userId);
        if (postForm != null) {

            model.addAttribute("postForm", postForm);
            return "EditPost"; // Serve the new EditPost page
        } else {

            model.addAttribute(Identifiers.ERR_MESSAGE, "Post not found or you don't have permission to edit.");
            return Identifiers.REDIRECT_DASHBOARD;
        }
    }


    // Update an existing post
    @PostMapping("/posts/edit")
    public String updatePost(@ModelAttribute("postForm") PostForm postForm, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Identifiers.REDIRECT_LOGIN; // Redirect to login if session is invalid
        }
        boolean isPostUpdated = postService.updatePost(postForm, userId);
        if (isPostUpdated) {
            return Identifiers.REDIRECT_DASHBOARD;
        } else {
            model.addAttribute(Identifiers.ERR_MESSAGE, "Failed to update the post. Please try again.");
            return Identifiers.NEW_POST;
        }
    }

    // Delete a post
    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam("postId") Integer postId, Model model) {
        Integer userId = (Integer) session.getAttribute(Identifiers.USER_ID);
        if (userId == null) {
            return Identifiers.REDIRECT_LOGIN; // Redirect to login if session is invalid
        }
        boolean isDeleted = postService.deletePostById(postId, userId);
        if (isDeleted) {
            return Identifiers.REDIRECT_DASHBOARD;
        } else {
            model.addAttribute(Identifiers.ERR_MESSAGE, "Failed to delete the post.");
            return Identifiers.REDIRECT_DASHBOARD;
        }
    }
    @GetMapping("/logout")
    public String logout() {
    	
    	session.invalidate();
    	
		return "redirect:/";
    	
    }
}

