package in.somanath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.somanath.binding.LoginForm;
import in.somanath.binding.RegistrationForm;
import in.somanath.identify.Identifiers;
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
		
		return Identifiers.REGISTRATION;
	}
	
	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute("regform") RegistrationForm regform, Model model) {
	        try {
	            userService.saveUser(regform);
	            return Identifiers.REDIRECT_LOGIN; // Redirect to login page after successful registration
	        } catch (EmailAlreadyExistsException e) {
	            model.addAttribute(Identifiers.ERROR, "Email already exists");
	            return Identifiers.REGISTRATION;
	        } catch (InvalidPasswordException e) {
	            model.addAttribute(Identifiers.ERROR, "Password must contain at least one unique letter from a to z");
	            return Identifiers.REGISTRATION;
	        }
	    }
	
	@GetMapping("/login")
	public String loginPageView(Model model) {
		
		model.addAttribute("loginform", new LoginForm());
		
		
		return Identifiers.LOG_IN;
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("loginform") LoginForm loginform, Model model) {
	    try {
	        // Assuming authenticateUser returns the authenticated user's ID
	        Integer userId = userService.authenticateUser(loginform.getEmail(), loginform.getPassword());
             
	        if (userId != null) {  // If the user is authenticated and the ID is not null
	            session.setAttribute("userId", userId);  // Set userId in session
	            return Identifiers.REDIRECT_DASHBOARD;  // Redirect to dashboard
	        } else {
	            model.addAttribute(Identifiers.ERROR, "Invalid email or password");
	            return Identifiers.LOG_IN;
	        }
	    } catch (InvalidLoginException e) {
	        model.addAttribute(Identifiers.ERROR, e.getMessage());
	        return Identifiers.LOG_IN;
	    }
	}

	
	

}
