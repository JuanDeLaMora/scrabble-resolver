package com.bluenile.scrabble.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class ScrabbleResponse implements Serializable {

    @JsonProperty("status")
    private ResponseStatus status;
    @JsonProperty("words")
    private List<String> words;
}
