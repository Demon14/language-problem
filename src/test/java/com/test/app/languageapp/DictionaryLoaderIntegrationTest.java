package com.test.app.languageapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DictionaryLoaderIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;


    private DictionaryLoader dictionaryLoader = new DictionaryLoader();

    @Before
    public void setUp() {

    }

    @Test
    public void test_loadDictionaries_Single_File() {
        ReflectionTestUtils.setField(dictionaryLoader, "fileNames", "ENGLISH.1.dic");
        List<String> names = Arrays.asList("ENGLISH.1.dic");
        dictionaryLoader.loadDictionaries();
        Map<String, Set<String>> dictionaries = dictionaryLoader.getDictionaries();
        assertEquals(1, dictionaries.size());
        assertTrue(dictionaries.containsKey("ENGLISH"));
    }

    @Test
    public void test_loadDictionaries_Many_Files() {
        ReflectionTestUtils.setField(dictionaryLoader, "fileNames", "ENGLISH.1.dic,English.2.dic,FRENCH.1.dic");
        List<String> names = Arrays.asList("ENGLISH.1.dic");
        dictionaryLoader.loadDictionaries();
        Map<String, Set<String>> dictionaries = dictionaryLoader.getDictionaries();
        assertEquals(2, dictionaries.size());
        assertTrue(dictionaries.containsKey("ENGLISH"));
        assertTrue(dictionaries.containsKey("FRENCH"));
    }

    @Test
    public void testLoadDictionaries_Content_LowerCase() {
        ReflectionTestUtils.setField(dictionaryLoader, "fileNames", "ENGLISH.1.dic");
        List<String> names = Arrays.asList("ENGLISH.1.dic");
        dictionaryLoader.loadDictionaries();
        Map<String, Set<String>> dictionaries = dictionaryLoader.getDictionaries();
        Set<String> res = dictionaries.get("ENGLISH");
        assertNotNull(res);
        assertEquals(2, res.size());
        assertTrue(res.contains("computer"));
        assertTrue(res.contains("rules"));
    }
}