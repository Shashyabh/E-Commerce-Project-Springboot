package com.store.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //Error message
    String message() default "Invalid Image name";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
