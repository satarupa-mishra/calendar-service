package com.db.projects.calendarservice.validations;

import com.db.projects.calendarservice.constants.CalendarConstant;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation class for checking if eventType is Meeting/Task
 * if not , input api will not be validated
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomEventValidation.class)
public @interface CustomEventValidator {
    String message() default "Please provide a valid eventType: Meeting/Task";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class CustomEventValidation implements ConstraintValidator<CustomEventValidator, String> {
    @Override
    public void initialize(CustomEventValidator customEvent) {

    }

    @Override
    public boolean isValid(String event, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValidEventType = false;
        if(CalendarConstant.VALID_EVENT_TYPE_LIST.contains(event)){
            isValidEventType = true;
        }
        return isValidEventType;
    }


}
