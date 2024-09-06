package in.somanath.binding;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationForm {
	
    private Integer userId;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String password;

}
