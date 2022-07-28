Feature:Validating weather API

  Scenario Outline: scenario 1.validating weather API for particular city
    When User sends a get request to "weatherbyCityId" to fetch the weather of "<city>"
    Then Response with success status code  of value <code> should be returned
    And response should have value "<city>" at path "name"
    And response should have value "<timzone>" at path "timezone"
    And response should have value "<country>" at path "sys.country"
    And response should have value "<base>" at path "base"


    Examples:
      | city      | code | timzone | country | base     |
      | London    | 200  | 3600    | GB      | stations |
      | Mumbai    | 200  | 19800   | IN      | stations |
      | Ahmedabad | 200  | 19800   | IN      | stations |
      | Tokyo     | 200  | 32400   | JP      | stations |
      | Shanghai  | 200  | 28800   | CN      | stations |

  Scenario Outline: scenario 2.validating weather API for city id
    When User sends a get request to "weatherbyCitycode" to fetch the weather of "<citycode>"
    Then Response with success status code  of value <code> should be returned
    And response should have value "<city>" at path "name"
    And response should have value "<country>" at path "sys.country"
    And response should have value "<timezone>" at path "timezone"
    And response should have value "stations" at path "base"

    Examples:
      | city              | code | citycode | country | timezone |
      | Bomdila           | 200  | 1275334  | IN      | 19800    |
      | Morenci           | 200  | 5305503  | US      | -25200   |
      | Westhampton Beach | 200  | 5144090  | US      | -14400   |
      | Vesoul            | 200  | 2969562  | FR      | 7200     |
      | Faia              | 200  | 2739782  | PT      | 3600     |

  Scenario Outline: scenario 3.validating weather API by ZIP code
    When User sends a get request to "weatherbyzipcode" to fetch the weather of "<zipcode>"
    Then Response with success status code  of value <code> should be returned
    And response should have value "<city>" at path "name"
    And response should have value "<country>" at path "country"
    And response should have value "<lat>" at path "lat"

    Examples:
      | zipcode  | code | city          | country | lat     |
      | E14,GB   | 200  | London        | GB      | 51.4969 |
      | 90210,US | 200  | Beverly Hills | US      | 34.0901 |


  Scenario Outline: scenario 4.validating weather API by country code
    When User sends a get request to "weatherbycountrycode" to fetch the weather of "<countrycode>"
    Then Response with success status code  of value <code> should be returned
    And response should have value "<city>" at path "name"
    And response should have value "<country>" at path "sys.country"
    And response should have value "<lat>" at path "coord.lat"
    And print all the headers

    Examples:
      | countrycode | code | city    | country | lat     |
      | Bomdila,IN  | 200  | Bomdila | IN      | 27.25   |
      | London,uk   | 200  | London  | GB      | 51.5085 |




