package com.bluenile.scrabble.model.request;

import com.bluenile.scrabble.model.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ScrabbleValidator {

    public void validateScrabbleRequest(String letters) throws ValidationException {

        if(StringUtils.isBlank(letters) || !StringUtils.isAlpha(letters) || letters.length() > 7)
        {
            throw new ValidationException("Input must be letters only and its length should not be greater than 7 characters");
        }

    }
}
