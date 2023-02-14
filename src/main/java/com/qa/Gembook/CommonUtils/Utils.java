package com.qa.Gembook.CommonUtils;

import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.ui.utils.DriverAction;
import com.gemini.generic.ui.utils.DriverManager;
import com.qa.Gembook.Locators.Locators;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils extends DriverAction {
    // @Rahul Tagra, @Deeksha Singh
    @Given("User clicks on signIn Button")
    public void signIn() {
        waitUntilElementAppear(Locators.signInBtn, 20);
        click(Locators.signInBtn, "Click on Sign In Button", "Clicked Sign In Button"); // Click on Sign in Button
    }

    // @Rahul Tagra, @Deeksha Singh
    @When("User enters the {string}")
    public void enterCredentials(String credentialType) throws IOException {
        List<String> browserWindows = new ArrayList<>(getWindowHandles()); // Get all browser windows
        switchToWindow(browserWindows.get(1)); // Switch focus to 2nd browser window
        waitUntilElementAppear(Locators.credentials(credentialType), 10);
        typeText(Locators.credentials(credentialType), readProperties(credentialType)); // Enter Username or Password to login
        waitUntilElementAppear(Locators.nextBtn, 10);
        click(Locators.nextBtn);
        waitSec(5);
    }

    // @Rahul Tagra, @Deeksha Singh
    @Then("User logins into the application")
    public void login() {
        waitUntilElementAppear(Locators.nextBtn, 10);
        getElement(Locators.nextBtn).click(); // User clicks on yes Button
        List<String> browserWindows = new ArrayList<>(getWindowHandles());
        switchToWindow(browserWindows.get(0)); // Switch focus back to 1st window
        waitSec(5);
    }

    // @Rahul Tagra, @Deeksha Singh
    public String readProperties(String property) throws IOException { // Function to read Data from Properties File
        FileReader read = new FileReader("src/main/resources/config.properties");
        Properties credential = new Properties();
        credential.load(read);
        return credential.getProperty(property);
    }

    // @Rahul Tagra, @Deeksha Singh
    @And("Verify user is logged into the application or not")
    public void verifyLogin() {
        String expectedUrl = "https://gembook.geminisolutions.com/#/dashboard";
        if (getCurrentURL().contains(expectedUrl) && isExist(Locators.gembookLogo) && isExist(Locators.logoHeader) && isExist(Locators.profileImage)) {
            GemTestReporter.addTestStep("Verify if User is logged into the application", "User logins into the Gembook application", STATUS.PASS, takeSnapShot());
        } else {
            GemTestReporter.addTestStep("Verify if User is logged into the application", "User is unable to login into the Gembook application", STATUS.FAIL, takeSnapShot());
        }
    }
}
