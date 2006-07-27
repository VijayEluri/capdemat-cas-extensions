package fr.cg95.sso.cas.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fr.cg95.sso.cas.principal.UsernamePasswordAuthorityCredentials;

public class UsernamePasswordAuthorityCredentialsValidator implements Validator {

    public boolean supports(Class arg0) {
        return UsernamePasswordAuthorityCredentials.class.isAssignableFrom(arg0);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
                "required.username", null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", null);
    }
}
