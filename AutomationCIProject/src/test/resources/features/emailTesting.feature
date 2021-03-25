#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Testing mailchimp registration function

  Background: Website is open
	Given I have opened the webite

  Scenario Outline: Scenario outline tests
    Given I want to enter <type1> email adress
    And I want to enter a <type2> username
    And I want to enter a password
    And I tick the box
    Then I press the sign up button
    And I check the result

    Examples: 
      |type1 			|type2			|
      |"regular" 	|"regular"	|
      |"regular"	|"long"			|
      |"regular"	|"exists" 	|
      |"empty"		|"regular" 	|
