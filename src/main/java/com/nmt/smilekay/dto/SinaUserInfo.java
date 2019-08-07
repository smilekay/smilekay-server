package com.nmt.smilekay.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SinaUserInfo {
    private long id;
    private String idstr;
    private String screen_name;
    private String name;
    private String province;
    private String city;
    private String location;
    private String profile_image_url;
    private String gender;
}
