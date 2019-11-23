package com.practice.todo.validation;

import org.springframework.validation.Errors;

public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingErrors(Errors errors) {
        ToDoValidationError error = new ToDoValidationError(
                "Validation failed. "
                        .concat(String.valueOf(errors.getErrorCount())).concat(" error/s."));

        errors.getFieldErrors().forEach(fieldError -> {
            error.addValidationError(fieldError.getField().concat(" ").concat(fieldError.getDefaultMessage()));
        });
        /*errors.getAllErrors().forEach(objectError->{
            error.addValidationError(objectError. + " " + objectError.getDefaultMessage());
        });*/
        return error;
    }
}
