package com.test.app.languageapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This is component that loads dictionaries based on defined properties concurrently
 */
@Component
public class DictionaryLoader {

    @Value("${file.names}")
    private String fileNames;


    private Map<String, Set<String>> dictionaries = new ConcurrentHashMap<>();


    /**
     *  this is main trigger that dictionaries are still loading
     */
    AtomicInteger counter = new AtomicInteger(0);


    /**
     * it will return list of dictionaries only after initial loading is done
     * @return
     */
    public Map<String, Set<String>> getDictionaries() {
        // wait until loading will be completed
        while (counter.get() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return dictionaries;
    }

    /**
     * loads dictionaries from resources at parallel way
     */
    public void loadDictionaries() {

        List<String> resourceNames = Arrays.asList(fileNames.split(","));

        ExecutorService  exec = Executors.newCachedThreadPool();
        for (String name : resourceNames){
            counter.addAndGet(1);
            exec.submit(() -> {
                try {
                    loadSingleDictionary(name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * this is single dictionary loading method that runs at parallel for each file separately
     *
     * @param name
     * @throws IOException
     */
    private void loadSingleDictionary(String name) throws IOException {

        if (name == null || name.indexOf(".") < 0) {
            return;
        }
        // define language based on name
        String language = name.substring(0, name.indexOf(".")).toUpperCase();
        // getting already saved words linked to the language
        Set<String> words = dictionaries.containsKey(language) ?
                dictionaries.get(language) :
                new HashSet<>();
        // create buffer reader
        File file = new ClassPathResource(name).getFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Set<String> newWords = new HashSet<>();
        boolean noMoreLine = false;
        // reads all words from resource to temporary set
        while (!noMoreLine) {

            String line = reader.readLine();

            if (line == null){
                    noMoreLine = true;
                    continue;
            }
            // add all words from buffer to given dictionary set. all words are saved at lower case,
            // all special characters are removed before the line will be split
            newWords.addAll(Arrays.asList(line.toLowerCase()
                    .replaceAll("[^a-zA-Z ]", " ")
                    .split(" ")));
        }
        // updates words set associated with the language at map
        synchronized (dictionaries) {
            words.addAll(newWords);
            dictionaries.put(language, words);
        }
        counter.addAndGet(-1);

    }





}
