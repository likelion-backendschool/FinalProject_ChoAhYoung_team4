package com.ll.final_project.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    // Throwable or Exception 을 상속 받을 경우 -> Throw & Catch 문 사용해야함
    // RunTimeException -> 런타임에 종료되므로 Throw & Catch 문 사용 안함
    public DataNotFoundException(String message) {
        super(message);
    }
}
