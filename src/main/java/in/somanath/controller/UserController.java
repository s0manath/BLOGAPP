package in.somanath.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	@GetMapping("/register")
	public String viewRegistrationPage() {
		
		
		return "Registration";
	}
	
	@PostMapping("/register")
	public String registerUser() {
		
		
		
		
		return"Login";
	}
	
	@GetMapping("/login")
	public String loginPageView() {
		
		
		return"Login";
	}
	
	@PostMapping
	public String loginUser() {
		
		
		return "Dashbord";
	}

}
