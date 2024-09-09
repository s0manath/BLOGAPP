package in.somanath.service;

import in.somanath.binding.RegistrationForm;
import in.somanath.service.exception.EmailAlreadyExistsException;
import in.somanath.service.exception.InvalidLoginException;
import in.somanath.service.exception.InvalidPasswordException;


public interface UserService {

	void saveUser(RegistrationForm regform) throws EmailAlreadyExistsException, InvalidPasswordException;;
	
	 Integer authenticateUser(String email, String password) throws InvalidLoginException;
}
