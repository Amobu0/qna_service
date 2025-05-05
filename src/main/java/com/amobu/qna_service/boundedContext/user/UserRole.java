package com.amobu.qna_service.boundedContext.user;

import lombok.Getter;

// enum 은 객체를 싱클톤으로 쓰고 싶다. + 객체의 개수가 무조건 정해져 있다.
// 코드의 안정성을 확보하기 위해 사용
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
