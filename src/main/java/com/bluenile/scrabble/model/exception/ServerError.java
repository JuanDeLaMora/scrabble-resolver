package com.bluenile.scrabble.model.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Server Error")
@Data
public class ServerError {
    @ApiModelProperty(example = "Internal server error")
    private String errorType;

    @ApiModelProperty(example = "Application Exception:: Error processing request. Contact Application Support")
    private String errorMessage;

    @ApiModelProperty(example = "optional")
    private String errorSource;
}
