package in.somanath.binding;

import java.util.List;

import in.somanath.entity.Post;
import in.somanath.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashbordResponse {
	
	private User user;
	
	private List<Post> posts;
	
	

}
