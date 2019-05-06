package com.test.app.languageapp;

import com.test.app.languageapp.model.SelectionItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LanguageSelectorService {

    private final DictionaryLoader dictionaryLoader;

    public LanguageSelectorService(DictionaryLoader dictionaryLoader) {
        this.dictionaryLoader = dictionaryLoader;
        dictionaryLoader.loadDictionaries();
   }


    /**
     * returns the list of languages that matched srcText
     * the list of one element will be returned if strcText fully matched to single dictionary
     *
     * @param srcText
     * @return
     */
    public List<SelectionItem> selectLanguage(String srcText) {

        Map<String, Set<String>> dictionaries = dictionaryLoader.getDictionaries();
        if (dictionaries == null || srcText == null) {
            return null;
        }



        // converts srcText to set of unique words using space as a separator
        // as pre-requisite it replaces all non alpha numeric characters by space and converts String to lower cse
        Set<String> srcValues = new HashSet<>();
        srcValues.addAll(Arrays.asList(srcText.replaceAll("[^a-zA-Z ]", " ")
                .toLowerCase().split(  " ")));
        long inputSize = srcValues.size();

        // compare all dictionaries with given values

        List<SelectionItem> selectionItems = new ArrayList<>();

        for (String key : dictionaries.keySet()) {
            Set<String> words = dictionaries.get(key);

            // exclude all words at input stream that are not presented at given dictionary
            List<String> filteredValues = srcValues.stream().
                    filter(s -> words.contains(s)).collect(Collectors.toList());

            long matchedWordsCount = filteredValues.size();

            if (matchedWordsCount > 0) {
                double accuracy = matchedWordsCount * 100.0 / inputSize;

                SelectionItem response = new SelectionItem();
                response.setLanguage(key);
                response.setAccuracy(new BigDecimal(accuracy));
                // stop further processing of dictionaries and clear previous results
                // if whole input was matched with single dictionary
                if (matchedWordsCount == inputSize) {
                    selectionItems.clear();
                    selectionItems.add(response);
                    break;
                }
                else {
                    selectionItems.add(response);
                }

            }

        }

        // no language has been defined
        if (selectionItems.isEmpty()) {
            return null;
        }

        return selectionItems;
    }
}
