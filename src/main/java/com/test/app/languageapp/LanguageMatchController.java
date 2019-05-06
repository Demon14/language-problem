package com.test.app.languageapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * main application controller
 *
 */
@Controller
public class LanguageMatchController {

    private final LanguageSelectorService languageSelectorService;

    public LanguageMatchController(LanguageSelectorService languageSelectorService) {
        this.languageSelectorService = languageSelectorService;
    }

    /**
     * redirects to index.jsp
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * processes given text and forwards the result to match.jsp
     * @param srcText
     * @return
     */
    @PostMapping(path = "/match")
    public ModelAndView matchLanguage(@RequestParam(name = "text") String srcText) {
        ModelAndView model = new ModelAndView();
        model.addObject("selections", languageSelectorService.selectLanguage(srcText));
        return model;
    }

}
