package in.somanath.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.somanath.binding.RegistrationForm;
import in.somanath.entity.User;
import in.somanath.repo.UserRepository;
import in.somanath.service.UserService;
import in.somanath.service.exception.EmailAlreadyExistsException;
import in.somanath.service.exception.InvalidLoginException;
import in.somanath.service.exception.InvalidPasswordException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	 @Override
	    public void saveUser(RegistrationForm regform) throws EmailAlreadyExistsException, InvalidPasswordException {
		 	
		 //check email is present
	        if (userRepository.existsByEmail(regform.getEmail())) {
	            throw new EmailAlreadyExistsException("Email already exists");
	        }

	        if (!isValidPassword(regform.getPassword())) {
	            throw new InvalidPasswordException("Password must contain at least one unique letter from a to z");
	        }

	        // Convert UserDto to User entity and save
	        User user = new User();
	        user.setFirstname(regform.getFirstname());
	        user.setLastname(regform.getLastname());
	        user.setEmail(regform.getEmail());
	        user.setPassword(regform.getPassword()); // Encrypt the password before saving

	        userRepository.save(user);
	    }

	    private boolean isValidPassword(String password) {
	        // Check for at least one unique letter from a to z
	        Pattern pattern = Pattern.compile(".*[a-z].*");
	        return pattern.matcher(password).matches();
	    }

	    @Override
	    public boolean authenticateUser(String email, String password) throws InvalidLoginException {
	        User user = userRepository.findByEmail(email);
	        if (user == null || !user.getPassword().equals(password)) {
	            throw new InvalidLoginException("Invalid email or password");
	        }
	        return true;
	    }

	   
	}


