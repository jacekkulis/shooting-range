package com.validator;

import com.model.Referee;
import com.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RefereeValidator implements Validator {
    @Autowired
    private RefereeService refereeService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Referee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Referee referee = (Referee) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (referee.getName().length() < 2 || referee.getName().length() > 20) {
            errors.rejectValue("name", "Size.refereeForm.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "NotEmpty");
        if (referee.getSurname().length() < 2 || referee.getName().length() > 20) {
            errors.rejectValue("surname", "Size.refereeForm.name");
        }
        
        
        if (refereeService.findByFullName(referee.getName(), referee.getSurname()) != null) {
            errors.rejectValue("name", "Duplicate.refereeForm.fullName");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
    }
}
