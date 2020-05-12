package com.fruit.basedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class BaseAction<T extends BaseDto> {
    @Autowired
    public RestTemplate restTemplate;
}
