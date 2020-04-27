package com.fruit.template.email.action;


import com.fruit.template.email.dto.ServerResponse;
import com.fruit.template.email.service.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TestController")
@Slf4j
public class TestController {
    @Autowired
    private TestServiceImpl testService;

//    @ApiIdempotent
//    @PostMapping("testIdempotence")
//    public ServerResponse testIdempotence() {
//        return testService.testIdempotence();
//    }
//
//    @AccessLimit(maxCount = 5, seconds = 5)
//    @PostMapping("accessLimit")
//    public ServerResponse accessLimit() {
//        return testService.accessLimit();
//    }

//    @GetMapping("send")
//    public ServerResponse sendMail(@Validated Mail mail, Errors errors) {
//        if (errors.hasErrors()) {
//            String msg = errors.getFieldError().getDefaultMessage();
//            return ServerResponse.error(msg);
//        }
//
//        return testService.send(mail);
//    }

    @GetMapping("/send")
    public ServerResponse sendMail() {
//        if (errors.hasErrors()) {
//            String msg = errors.getFieldError().getDefaultMessage();
//            return ServerResponse.error(msg);
//        }

        return testService.send();
    }

    @GetMapping("/test")
    public String test() {
//        if (errors.hasErrors()) {
//            String msg = errors.getFieldError().getDefaultMessage();
//            return ServerResponse.error(msg);
//        }

        return testService.test();
    }
}
