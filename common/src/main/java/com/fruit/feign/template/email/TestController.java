package com.fruit.feign.template.email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
@FeignClient(name = "template/TestController")
public interface TestController {

        @GetMapping(value = "/test")
        public String test();
}
