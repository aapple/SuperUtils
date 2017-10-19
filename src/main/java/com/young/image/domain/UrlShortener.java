package com.young.image.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yaobin on 2017/10/18.
 */
@Getter
@Setter
@ToString
public class UrlShortener {
    private String kind;
    private String id;
    private String longUrl;
}
