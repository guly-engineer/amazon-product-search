Feature: Amazon product search
  As an Anonymous Amazon.co.uk customer
  I want to be able to search for an item
  so that I am able to focus the list of products to a specified area

  Background:
    Given I am on the "https://www.amazon.co.uk" page
    And I search for "socks"

  #Scenario: I can search an item
    #Then I am able to scroll through all search results
    #And I see that each item has following attributes:
    #  | thumbnail |
   #   | title     |
     # | rating    |
    #  | price     |


  Scenario: I can view more about an item
    When I click on the thumbnail of the 3. item
    Then I am able to view more about the item


