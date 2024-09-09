package in.somanath.service.impl;

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
	    // Check email is present
	    if (userRepository.existsByEmail(regform.getEmail())) {
	        throw new EmailAlreadyExistsException("Email already exists");
	    }

	    // Validate password
	    if (regform.getPassword() == null || regform.getPassword().isEmpty()) {
	        throw new InvalidPasswordException("Password cannot be null or empty");
	    }

	    if (!isValidPassword(regform.getPassword())) {
	        throw new InvalidPasswordException("Password must contain at least one unique letter from a to z");
	    }

	    // Convert RegistrationForm to User entity and save
	    User user = new User();
	    user.setFirstname(regform.getFirstname());
	    user.setLastname(regform.getLastname());
	    user.setEmail(regform.getEmail());
	    user.setPassword(regform.getPassword()); // Encrypt the password before saving

	    userRepository.save(user);
	}


	private boolean isValidPassword(String password) {
	    // Check for null or empty password
	    if (password == null || password.isEmpty()) {
	        return false;
	    }

	    // Check for at least one letter from a to z
	    for (char c : password.toCharArray()) {
	        if (Character.isLetter(c)) {
	            return true;
	        }
	    }

	    return false;
	}



	    @Override
	    public Integer authenticateUser(String email, String password) throws InvalidLoginException {
	        // Logic to authenticate the user
	        User user = userRepository.findByEmail(email);
	        
	        if (user != null && user.getPassword().equals(password)) {
	            return user.getUserId();  // Return the user's ID after successful authentication
	        } else {
	            throw new InvalidLoginException("Invalid email or password");
	        }
	    }


	   
	}


