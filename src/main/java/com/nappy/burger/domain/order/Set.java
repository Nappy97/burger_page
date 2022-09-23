package com.nappy.burger.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Set {

    SINGLE("SET_SINGLE", "사용자"),
    SET("SET_SET", "세트");

    private final String key;
    private final String title;
}
