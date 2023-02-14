Feature: Gembook -> News Feed -> Events Section

  Background: User Login into the Gembook Application
    Given User clicks on signIn Button
    When User enters the "username"
    When User enters the "password"
    Then User logins into the application

  Scenario: Verify if user is logged into the application
    And Verify user is logged into the application or not
