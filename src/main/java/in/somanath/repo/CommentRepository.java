package in.somanath.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.somanath.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("SELECT c FROM Comment c WHERE c.post.user.userId = :userId")
    List<Comment> findCommentsByUserId(Integer userId);

	List<Comment> findByPostPostId(Integer postId);

}
