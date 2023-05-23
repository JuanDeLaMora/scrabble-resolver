package com.bluenile.scrabble.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatus implements Serializable {
    @ApiModelProperty(example = "200")
    public int code;

    @ApiModelProperty(example = "Some Response Message")
    public String message;

    @ApiModelProperty(example = "Some Response Description")
    public String description;

    @ApiModelProperty(example = "1574347301067")
    public final long runId = Instant.now().getEpochSecond();
}
