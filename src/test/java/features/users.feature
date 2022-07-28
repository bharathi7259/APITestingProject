Feature: To verify user API

  Scenario: Get list of users in the page
    When User send a get request to fetch the users in the page
    Then list of users should be displayed
    And Status code "200" should be returned

    
  Scenario: To practice queryparams feature
    When User wants to access the weather Api
    Then Status code "200" should be returned

  Scenario Outline: : Create new users
    When User send a Post request to create user of "<name>" and "<Job>"
    Then Response with "<statuscode>" and  "<statusLine>" should be returned
    And response should be displayed and validate for "<name>"

    Examples:
      | name     | Job    | statuscode | statusLine           |
      | bharathi | QA     | 201        | HTTP/1.1 201 Created |
      | morpheus | leader | 201        | HTTP/1.1 201 Created |

