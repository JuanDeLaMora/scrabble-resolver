package com.bluenile.scrabble.model.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Client Error")
@Data
public class ClientError {
    @ApiModelProperty(example = "Bad Request")
    private String errorType;

    @ApiModelProperty(example = "Application Exception:: Locale not supported")
    private String errorMessage;

    @ApiModelProperty(example = "optional")
    private String errorSource;
}
