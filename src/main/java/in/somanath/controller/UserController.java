package in.somanath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.somanath.binding.LoginForm;
import in.somanath.binding.RegistrationForm;
import in.somanath.service.UserService;
import in.somanath.service.exception.EmailAlreadyExistsException;
import in.somanath.service.exception.InvalidLoginException;
import in.somanath.service.exception.InvalidPasswordException;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/register")
	public String viewRegistrationPage(Model model) {
		
		model.addAttribute("regform", new RegistrationForm());
		
		return "Registration";
	}
	
	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute("regform") RegistrationForm regform, Model model) {
	        try {
	            userService.saveUser(regform);
	            return "redirect:/login"; // Redirect to login page after successful registration
	        } catch (EmailAlreadyExistsException e) {
	            model.addAttribute("error", "Email already exists");
	            return "Registration";
	        } catch (InvalidPasswordException e) {
	            model.addAttribute("error", "Password must contain at least one unique letter from a to z");
	            return "Registration";
	        }
	    }
	
	@GetMapping("/login")
	public String loginPageView(Model model) {
		
		model.addAttribute("loginform", new LoginForm());
		
		
		return"Login";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("loginform") LoginForm loginform, Model model) {
	    try {
	        // Assuming authenticateUser returns the authenticated user's ID
	        Integer userId = userService.authenticateUser(loginform.getEmail(), loginform.getPassword());
             System.out.println(userId);
	        if (userId != null) {  // If the user is authenticated and the ID is not null
	            session.setAttribute("userId", userId);  // Set userId in session
	            return "redirect:/dashboard";  // Redirect to dashboard
	        } else {
	            model.addAttribute("error", "Invalid email or password");
	            return "Login";
	        }
	    } catch (InvalidLoginException e) {
	        model.addAttribute("error", e.getMessage());
	        return "Login";
	    }
	}

	
	

}
