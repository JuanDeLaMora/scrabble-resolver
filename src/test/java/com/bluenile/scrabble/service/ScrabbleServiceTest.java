package com.bluenile.scrabble.service;

import com.bluenile.scrabble.model.request.ScrabbleValidator;
import com.bluenile.scrabble.model.response.ScrabbleResponse;
import com.bluenile.scrabble.repository.ScrabbleDictionary;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class ScrabbleServiceTest {

  @Spy
  private ScrabbleValidator scrabbleValidator;
  @Spy
  private ScrabbleDictionary dictionary;
  @InjectMocks
  private ScrabbleService scrabbleService;

  @Test
  public void testGetWordsReturnedFromThreeLetterInput() {
    List<String> wordsToCheck = List.of("hat", "th", "ah", "ha", "at", "a");

    ScrabbleResponse response = scrabbleService.getWords("tha");
    assertNotNull(response);
    assertEquals(response.getWords().size(), 6);

    for(int i = 0; i < wordsToCheck.size(); i++) {
      assertEquals(response.getWords().get(i), wordsToCheck.get(i));
    }
  }

  @Test
  public void testGetNoWordsReturnedFromInput() {

    ScrabbleResponse response = scrabbleService.getWords("zzz");
    assertNotNull(response);
    assertEquals(response.getWords().size(), 0);
  }

  @Test
  public void testGetWordsReturnedFromSevenLetterInput() {

    ScrabbleResponse response = scrabbleService.getWords("eobamgl");
    assertNotNull(response);
    assertEquals(response.getWords().size(), 90);
  }

  @Test
  public void testGetWordsForInvalidInputLength() {

    ScrabbleResponse response = scrabbleService.getWords("abcdefgh");
    assertNotNull(response);
    assertEquals(response.getStatus().code, 400);
  }

  @Test
  public void testGetWordsForInvalidInputData() {

    ScrabbleResponse response = scrabbleService.getWords("abcd456");
    assertNotNull(response);
    assertEquals(response.getStatus().code, 400);
  }
}
