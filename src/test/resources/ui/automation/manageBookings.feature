@test
Feature: Hotel Booking
  As a hotel manager
  I want to be able to add and remove reservations
  So that I can successfully manage my hotel

  Scenario: Add new booking
    Given I am on the Hotel management app
    When I add a new reservation for
      | firstname | Marc     |
      | surname   | Marcuson |
      | price     | 500      |
      | deposit   | false    |
    Then I should see it displayed in the list of reservations


  Scenario: Remove an existing booking
    Given I am on the Hotel management app
    And I can see a reservation that I want to remove
    When I remove the reservation
    Then I should see it disappear from the list