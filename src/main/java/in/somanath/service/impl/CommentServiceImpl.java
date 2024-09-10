package in.somanath.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.somanath.entity.Comment;
import in.somanath.repo.CommentRepository;
import in.somanath.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

	@Override
	public List<Comment> findCommentsByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return commentRepository.findCommentsByUserId(userId);
	}

	 @Override
	    public void deleteCommentById(Integer commentId) {
	        commentRepository.deleteById(commentId);  // Delete comment by ID
	    }

}
