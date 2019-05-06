package com.test.app.languageapp.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * main model that is created on service layer and passed to view layer
 */
@Data
public class SelectionItem {

    /**
     * matched language name at upper case
     */
    private String language;
    /**
     * represents result accuracy, range from 1 to 100.
     * i.e. fully matched result will have 100
     */
    private BigDecimal accuracy;
}
