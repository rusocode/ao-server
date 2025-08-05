package com.ao.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * Simple validation service using Hibernate Validator (Jakarta Validation standard).
 */

public class ValidatorService {


    /**
     * Factory for creating validator instances using the default configuration from Jakarta Validation (formerly known as Bean
     * Validation).
     */
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    /** Get a validator from the factory. */
    private static final Validator validator = factory.getValidator();

    /**
     * Checks if the given character name is valid.
     *
     * @param name character name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean validCharacterName(String name) {
        CharacterNameDto dto = new CharacterNameDto(name);
        Set<ConstraintViolation<CharacterNameDto>> violations = validator.validate(dto);
        return violations.isEmpty();
    }

    /**
     * Checks if the given email address is valid.
     *
     * @param email email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean validEmail(String email) {
        EmailDto dto = new EmailDto(email);
        Set<ConstraintViolation<EmailDto>> violations = validator.validate(dto);
        return violations.isEmpty();
    }

    /**
     * DTO for character name validation.
     * <p>
     * DTO ("Data Transfer Object") is a design pattern for data transfer, grouping of related information, and data validation.
     */
    public record CharacterNameDto(
            @NotBlank(message = "Character name cannot be empty")
            @Size(min = 1, max = 5, message = "Character name must be between 1 and 30 characters")
            @Pattern(regexp = "^[A-Za-z][A-Za-z ]*", message = "Character name must start with letter and contain only letters and spaces") String name) {

        public CharacterNameDto(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }

    }

    /**
     * DTO for email validation.
     */
    public record EmailDto(@NotBlank(message = "Email cannot be empty")
                           @Email(message = "Invalid email format") String email) {

        public EmailDto(String email) {
            this.email = email;
        }

    }

}