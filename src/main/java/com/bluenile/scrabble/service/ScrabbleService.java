package com.bluenile.scrabble.service;

import com.bluenile.scrabble.model.exception.ValidationException;
import com.bluenile.scrabble.model.request.ScrabbleValidator;
import com.bluenile.scrabble.model.response.ResponseStatus;
import com.bluenile.scrabble.model.response.ScrabbleResponse;
import com.bluenile.scrabble.repository.ScrabbleDictionary;
import com.bluenile.scrabble.util.ScrabbleConstants;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Slf4j
@Service
public class ScrabbleService {

  @Autowired
  ScrabbleDictionary scrabbleDictionary;
  @Autowired
  ScrabbleValidator scrabbleValidator;

  ArrayList<String> permutations;
  String originalString;
  Map<Character,Integer> charCounts;
  Set<String> letterCombinations;

  /**
   * Returns a list of words that can be spelled from the given set of letters.
   * It is sorted by its Scrabble point value.
   *
   * @param letters The letters to form words from
   * @return A sorted list of words from highest to lowest Scrabble score
   */
  public ScrabbleResponse getWords(String letters) {

    ScrabbleResponse response = new ScrabbleResponse();
    ResponseStatus status = new ResponseStatus();

    try {
      log.info("[SCRABBLE SERVICE] [GET WORDS] - Received request for input '{}'", letters);

      scrabbleValidator.validateScrabbleRequest(letters);

      Set<String> letterCombinations = getLetterCombinations(letters);

      response.setWords(scrabbleDictionary.scrabbleWords(letterCombinations));

      status.setCode(HttpStatus.OK.value());
      status.setMessage(HttpStatus.OK.name());
      status.setDescription(ScrabbleConstants.SUCCESS_MESSAGE);
    } catch (ValidationException e) {
      log.error("[SCRABBLE SERVICE] [GET WORDS] - Validation error on provided letters. {}", e.getMessage());

      status.setCode(HttpStatus.BAD_REQUEST.value());
      status.setMessage(HttpStatus.BAD_REQUEST.name());
      status.setDescription(ScrabbleConstants.VALIDATION_ERROR + e.getMessage());

    } catch (Exception ex) {
      log.error("[SCRABBLE SERVICE] [GET WORDS] - Error while obtaining valid words from Scrabble dictionary: {}",
              Throwables.getRootCause(ex).getMessage());

      status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      status.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
      status.setDescription(ScrabbleConstants.SYSTEM_ERROR);
    }

    response.setStatus(status);
    return response;
  }

  /**
   * Constructor. Note that after the constructor is called, populatePermutationsList() must be called to
   * actually generate the permutations.
   *
   * @param letters The string we will find all combinations and permutations of.
   * @return
   */
  private Set<String> getLetterCombinations(String letters) {
    letterCombinations = new TreeSet<>();
    permutations = new ArrayList<>();
    originalString = letters;
    charCounts = new TreeMap<>();
    char[] temp = originalString.toCharArray();
    for (char c : temp)
      charCounts.put(c, count(originalString, c));

    populatePermutationsList();
    columnPrint(letters.length());

    return letterCombinations;
  }

  /**
   * A method to find the number of times a character appears in a string.
   *
   * @param string	A string to search in.
   * @param c	A character. We will count how many times it appears in s.
   */
  private int count(String string, char c) {
    char[] temp = string.toCharArray();
    int charCount = 0;
    for(char d: temp) {
      if(d==c) charCount++;
    }
    return charCount;
  }

  /**
   * Sorts the list of permutations / combinations and then returns them.
   */
  private List<String> getPermutations() {
    Collections.sort(permutations);
    return permutations;
  }

  /**
   * Using recursion, this method goes through the string and generates all possible permutations and combinations.
   * <p>Algorithm:
   * <p>Step 1: take the letters to begin with a, b, c, d...etc
   * <p>Step 2: put these in an array list of base permutations
   * <p>Step 3: traverse the original string concatenating the individual letters (unless already used in the base permutation) to the base permutations
   * <p>Step 4: pass the new permutations from step 3 into step 2 and do step 3 again
   *
   *  @param basePermutations	A list of the permutations / combinations we are going to build on.
   */
  private void RecursivelyPermute(ArrayList<String> basePermutations) {
    ArrayList<String> newPermutations = new ArrayList<>();
    char[] temp = originalString.toCharArray();

    for(String s : basePermutations) {
      if(s.length() < originalString.length()) {
        for(char c : temp) {
          if(count(s,c) < charCounts.get(c)) {
            newPermutations.add(s + c);
            letterCombinations.add(s + c);
          }
        }
      }
    }
    if(newPermutations.size() > 0) {
      permutations.addAll(newPermutations);
      RecursivelyPermute(newPermutations);
    }
  }

  /**
   * Given a number of columns, prints to the console the permutations and combinations found.
   *
   * @param columns	The number of columns to format the output into.
   */
  private void columnPrint(int columns) {
    //calling getPermutations() here so that the permutations get sorted
    int rows = getPermutations().size() / columns;

    //print 'rows' number of rows with 'columns' number of columns
    for(int i=0; i < rows; i++) {
      for(int j=0; j < columns; j++) {
        letterCombinations.add(permutations.get(i + (j*rows)));
      }
    }
  }

  /**
   * This method must be invoked after construction in order for the permutations list to have any content.
   */
  private void populatePermutationsList() {
    char[] temp = originalString.toCharArray();

    //pre-loading for the RecursivelyPermute method. Please see notes there.
    for(char c : temp) {
      //do not add duplicate letters (if there is more than one 'a' for instance, add only one)
      if(charCounts.get(c) == 1 || (charCounts.get(c) > 1 && !permutations.contains(String.valueOf(c))))
        permutations.add(String.valueOf(c));
    }
    RecursivelyPermute(permutations);
  }

}
