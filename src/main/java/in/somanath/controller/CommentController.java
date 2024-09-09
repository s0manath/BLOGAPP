package in.somanath.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
	
	@PostMapping("comments")
	public String saveComment() {
		
		return "";
	}

}
