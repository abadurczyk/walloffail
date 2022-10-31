Feature: Customer functionalities
  This feature contains a list of functionalities related to customer

  Scenario: A new fail is created
    When A new fail is created with the description "foo"
    Then 1 fail(s) exist

  Scenario: A comment is added to a fail
    Given A new fail is created with the description "foo"
    When A comment is added with text "super duper" and rating of 2 stars

