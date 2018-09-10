package com.gura.spring03;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
   @RequestMapping("/home")
   public String home(Locale locale, Model model) {
      logger.info("/home.do 요청 처리");
      return "home";
   }
}
