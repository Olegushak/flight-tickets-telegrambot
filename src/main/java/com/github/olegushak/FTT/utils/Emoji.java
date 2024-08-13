package com.github.olegushak.FTT.utils;

import lombok.Getter;

@Getter
public enum Emoji {
    DEP_AIRPLANE("\uD83D\uDEEB"),
    ARR_AIRPLANE("\uD83D\uDEEC"),
    DEP_DATE("\uD83D\uDDD3"),
    RETURN_DATE("\uD83D\uDCC6");

    private final String picture;

     Emoji(String picture){
        this.picture = picture;
    }
}
