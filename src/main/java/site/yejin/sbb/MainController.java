package site.yejin.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/questions";
    }

    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        System.out.println("index");
        return "/sbb main page";
    }

    @RequestMapping("/post")
    public String home() {
        return "post";
    }
}