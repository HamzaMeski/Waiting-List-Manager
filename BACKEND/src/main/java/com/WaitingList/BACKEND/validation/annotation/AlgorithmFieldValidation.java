package com.WaitingList.BACKEND.validation.annotation;

import com.WaitingList.BACKEND.validation.validator.AlgorithmFieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlgorithmFieldValidator.class)
@Documented
public @interface AlgorithmFieldValidation {
    String message() default "Invalid fields for the selected algorithm";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 