package com.test.app.languageapp;

import com.test.app.languageapp.model.SelectionItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LanguageSelectorServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Mock
    private DictionaryLoader dictionaryLoader;

    @InjectMocks
    private LanguageSelectorService languageSelectorService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(languageSelectorService);
    }

    @Test
    public void testSelectLanguageEmptyInput() {
        assertNull(languageSelectorService.selectLanguage(null));
        assertNull(languageSelectorService.selectLanguage(""));
    }

    @Test
    public void testSelectLanguageEmptyDictionaries() {
        when(dictionaryLoader.getDictionaries()).thenReturn(new HashMap<>());

        assertNull(languageSelectorService.selectLanguage("computer"));

        verify(dictionaryLoader, times(1)).loadDictionaries();
        verify(dictionaryLoader, times(1)).getDictionaries();
    }

    @Test
    public void testSelectLanguageSingleDictionarySingleWord() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(false));
        List<SelectionItem> res  = languageSelectorService.selectLanguage("computer");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("ENGLISH", res.get(0).getLanguage());
    }

    @Test
    public void testSelectLanguageSingleDictionarySingleWordMixedCase() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(false));
        List<SelectionItem> res  = languageSelectorService.selectLanguage("Computer");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertNotNull(res.get(0));
        assertEquals("ENGLISH", res.get(0).getLanguage());
    }

    @Test
    public void testSelectLanguageSingleDictionaryText() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(false));
        List<SelectionItem> res = languageSelectorService.selectLanguage("computer rules");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertNotNull(res.get(0));
        assertEquals("ENGLISH", res.get(0).getLanguage());
    }

    @Test
    public void testSelectLanguageSingleDictionaryTextWithSpecialCharacters() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(false));
        List<SelectionItem> res = languageSelectorService.selectLanguage("Computer Rules!");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertNotNull(res.get(0));
        assertEquals("ENGLISH", res.get(0).getLanguage());
        assertEquals(new BigDecimal(100.0), res.get(0).getAccuracy());
    }
    @Test
    public void testSelectLanguageSingleDictionaryTextWithSpecialCharactersNoSpace() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(false));
        List<SelectionItem> res = languageSelectorService.selectLanguage("Computer,Rules!");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertNotNull(res.get(0));
        assertEquals("ENGLISH", res.get(0).getLanguage());
        assertEquals(new BigDecimal(100.0), res.get(0).getAccuracy());
    }

    @Test
    public void testSelectSingleLanguageMultipleDictionariesText() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(true));
        List<SelectionItem> englishResult = languageSelectorService.selectLanguage("computer rules");
        assertEquals("ENGLISH", englishResult.get(0).getLanguage());
        List<SelectionItem> frenchResult = languageSelectorService.selectLanguage("Paris");
        assertEquals("FRENCH" , frenchResult.get(0).getLanguage());

    }

    @Test
    public void testSelectLanguageSingleIncompleteDictionaryText() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(false));
        List<SelectionItem> res =  languageSelectorService.selectLanguage("Apple computer rules");
        assertNotNull(res);
        assertEquals("ENGLISH", res.get(0).getLanguage());
        assertEquals( new BigDecimal(66.66).doubleValue(), res.get(0).getAccuracy().doubleValue(), 0.01);
    }

    @Test
    public void testSelectLanguageMultipleDictionariesSharedText() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(true));
        List<SelectionItem> res =  languageSelectorService.selectLanguage("computer");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("ENGLISH", res.get(0).getLanguage());
        assertEquals( new BigDecimal(100.0).doubleValue(),
                res.get(0).getAccuracy().doubleValue(), 0.01);
    }

    @Test
    public void testSelectLanguageMultipleIncompleteDictionariesSharedText() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(true));
        List<SelectionItem> res =  languageSelectorService.selectLanguage("computer games");
        assertNotNull(res);
        assertEquals(2, res.size());
        assertEquals("ENGLISH", res.get(0).getLanguage());
        assertEquals( new BigDecimal(50.0).doubleValue(), res.get(0).getAccuracy().doubleValue(), 0.01);
        assertEquals("FRENCH", res.get(1).getLanguage());
        assertEquals( new BigDecimal(50.0).doubleValue(), res.get(1).getAccuracy().doubleValue(), 0.01);
    }

    @Test
    public void testSelectLanguageMultipleDictionariesPartialAndFullMatch() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(true));
        List<SelectionItem> res =  languageSelectorService.selectLanguage("Paris computer");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("FRENCH", res.get(0).getLanguage());
        assertEquals( new BigDecimal(100.0).doubleValue(), res.get(0).getAccuracy().doubleValue(), 0.01);
    }

    @Test
    public void testSelectLanguageMultipleDictionariesFullAndPartialMatch() {

        when(dictionaryLoader.getDictionaries()).thenReturn(mockDictionary(true));
        List<SelectionItem> res =  languageSelectorService.selectLanguage("computer rules");
        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("ENGLISH", res.get(0).getLanguage());
        assertEquals( new BigDecimal(100.0).doubleValue(), res.get(0).getAccuracy().doubleValue(), 0.01);
    }

    private Map<String, Set<String>> mockDictionary(boolean isSecondDictionary) {
       Map<String, Set<String>> dict = new HashMap<>();
       Set<String> words  = new HashSet<>();
       words.addAll(Arrays.asList("computer", "rules"));
       dict.put("ENGLISH", words);
       if (isSecondDictionary) {
           words  = new HashSet<>();
           words.addAll(Arrays.asList("computer", "paris"));
           dict.put("FRENCH", words);
       }
       return  dict;
    }

}