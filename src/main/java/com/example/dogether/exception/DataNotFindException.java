package com.example.dogether.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "질문을 찾을 수 없습니다.")
public class DataNotFindException extends RuntimeException{
    private static final long serialVersionID = 1L;
    //모든 클래스는 UID를 가지고 있는데 class내용이 변경되면 UID값도 바뀐다. 직렬화하여 통신하고 UID값이 정상인지 확인하는데 그 값이 바뀌게 되면 다른 Class로 인식한다. 이를 방지하기 위해 고유값으로 미리 명시해 주는 부분이다.
    public DataNotFindException(String message){
        super(message);
    }
}
