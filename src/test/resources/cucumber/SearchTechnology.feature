Feature: search for Technologies
  As a developer
  I would like to know if somebody has been using a specific technology
  So that I can ask for help or advise

  Scenario: direct search
    Given I am logged in as 'marcus.lacerda@gmail.com'
    When I enter search term 'Cucumber'
    And press the enter key
    Then I should see at least 1 card for 'Cucumber'
