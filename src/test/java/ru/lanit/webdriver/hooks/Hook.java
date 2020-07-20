package ru.lanit.webdriver.hooks;


import com.codeborne.selenide.Configuration;
import io.cucumber.java.Before;

public class Hook {
    @Before
    public void setUp() {
        Configuration.timeout = 10000;
    }
}
