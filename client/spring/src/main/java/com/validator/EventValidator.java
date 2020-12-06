package com.validator;

import com.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {
    
    boolean editable;
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
        Event event = (Event) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        if (event.getTitle().length() < 5 || event.getTitle().length() > 50) {
            errors.rejectValue("title", "Size.eventForm.title");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        if (event.getDescription().length() < 10 || event.getTitle().length() > 1000) {
            errors.rejectValue("description", "Size.eventForm.description");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeOfGun", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfCompetitors", "NotEmpty");
        if (event.getNumberOfCompetitors() < 2 || event.getNumberOfCompetitors() > 30) {
            errors.rejectValue("numberOfCompetitors", "Value.eventForm.numberOfCompetitors");
        }
        
        if (!editable)
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "img", "NotEmpty");
    }
}
