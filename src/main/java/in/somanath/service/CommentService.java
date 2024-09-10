package in.somanath.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.somanath.entity.Comment;

@Service
public interface CommentService {

	void saveComment(Comment comment);

	List<Comment> findCommentsByUserId(Integer userId);

	void deleteCommentById(Integer commentId);

}
