package jp.sun.rental.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.sun.rental.common.annotation.UniqueUserName;
import jp.sun.rental.domain.repository.UserRepository;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
	
	private UserRepository userRepository;
	
	public UniqueUserNameValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		
		if (userName == null || userName.isEmpty()) {
			return true;
		}
		
		try {
			int userId = userRepository.getUserIdByUserName(userName);
			
			if (userId != 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return true;
		}

	}
	
}
