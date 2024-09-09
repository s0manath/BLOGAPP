package in.somanath.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.somanath.entity.Post;
import in.somanath.entity.User;

public interface PostRepository  extends JpaRepository<Post, Integer>{

	   List<Post> findByUser(User user);
}
