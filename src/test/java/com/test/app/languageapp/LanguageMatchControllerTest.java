package com.test.app.languageapp;

import com.test.app.languageapp.model.SelectionItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LanguageMatchControllerTest {

    @Mock
    private LanguageSelectorService languageSelectorService;

    @InjectMocks
    private LanguageMatchController languageMatchController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(languageMatchController);
    }


    @Test
    public void testLanguageController() {
        List<SelectionItem> items = new ArrayList<>();
        SelectionItem item = new SelectionItem();
        item.setLanguage("ENGLISH");
        item.setAccuracy(new BigDecimal(100.0));
        items.add(item);
        when(languageSelectorService.selectLanguage(anyString())).thenReturn(items);
        ModelAndView model = languageMatchController.matchLanguage("Computer rules!");
        assertNotNull(model);
        assertNotNull(model.getModel());
        assertTrue(model.getModel().containsKey("selections"));
        List<SelectionItem> res = (List<SelectionItem>)model.getModel().get("selections");
        assertEquals("ENGLISH", res.get(0).getLanguage());
    }

}