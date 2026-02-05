package jp.sun.rental.common.validator;

import org.springframework.dao.EmptyResultDataAccessException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.sun.rental.common.annotation.UniqueEmail;
import jp.sun.rental.domain.repository.UserRepository;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
	
	private UserRepository userRepository;
	
	public UniqueEmailValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		
		if (email == null || email.isEmpty()) {
			return true;
		}

		try {
			int userId = userRepository.getUserIdByEmail(email);
			
			if (userId != 0) {
				return false;
			} else {
				return true;
			}
		} catch (EmptyResultDataAccessException e) {
		    return true; // 存在しない（＝ユニーク）
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
