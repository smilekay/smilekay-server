package com.nmt.smilekay.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SinaAccessToken {
    private String access_token;
    private long expires_in;
    private String remind_in;
    private String uid;
    private String isRealName;
}
