package com.bluenile.scrabble.controller;

import com.bluenile.scrabble.model.exception.ClientError;
import com.bluenile.scrabble.model.exception.ServerError;
import com.bluenile.scrabble.model.response.ScrabbleResponse;
import com.bluenile.scrabble.service.ScrabbleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@Api(tags = "Scrabble service")
@RequestMapping("/words")
public class ScrabbleController {

  @Autowired
  private ScrabbleService scrabbleService;

  @ResponseBody
  @GetMapping(value = "/{letters}", produces = "application/json")
  @ApiOperation("Retrieves all Scrabble valid words out of the letters provided")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = ScrabbleResponse.class),
          @ApiResponse(code = 400, message = "Bad Request", response = ClientError.class),
          @ApiResponse(code = 500, message = "Internal server error", response = ServerError.class)
  })
  public ResponseEntity<ScrabbleResponse> getWords(@ApiParam(value= "Scrabble letters", example="tha")
                                                   @PathVariable("letters")String letters) {

    log.info("[SCRABBLE CONTROLLER] [GET] - Start");
    ScrabbleResponse response = scrabbleService.getWords(letters.toLowerCase());

    log.info("[SCRABBLE CONTROLLER] [GET] - End");
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus().code));
  }
}
