package site.yejin.sbb.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/button")
    public String root() {
        return "test";
    }


    @RequestMapping("/aaa")
    @ResponseBody
    public String aaa() {
        return "aaa";
    }

    @RequestMapping("/bbb")
    @ResponseBody
    public String bbb() {
        return "bbb";
    }

}
