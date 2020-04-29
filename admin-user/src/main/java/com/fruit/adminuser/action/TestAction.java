package com.fruit.adminuser.action;

import com.fruit.feign.template.email.TestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestAction {
    @Autowired
    private TestController testController;

    @GetMapping("/test")
    public String getTest(){
        return testController.test();
    }
}
