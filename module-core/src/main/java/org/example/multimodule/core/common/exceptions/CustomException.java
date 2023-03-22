package org.example.multimodule.core.common.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.multimodule.core.common.enums.ErrorCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
