package in.somanath.binding;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
	
	private Integer postId;
	
	private String title;
	
	private String description;
	
	@Lob
	private String content;

}
