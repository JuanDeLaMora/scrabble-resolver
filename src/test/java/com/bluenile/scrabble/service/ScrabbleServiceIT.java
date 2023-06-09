package com.bluenile.scrabble.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrabbleServiceIT {

  @Autowired
  private ScrabbleService service;

  @Test
  public void testApplicationStartsUp() {
    Assert.assertNotNull(service);
  }
}
