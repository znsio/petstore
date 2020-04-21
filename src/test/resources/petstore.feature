Feature: Test the pet store api

Background:
* url "http://localhost:8080"

Scenario: Add pets
  When path "/pets"
  And request {"name": "Patch", "type": "dog", "status": "available"}
  And method PUT
  Then status 200
  When path "/pets"
  And request {"name": "Blackie", "type": "dog", "status": "available"}
  And method PUT
  Then status 200
  When path "/pets"
  And request {"name": "Socks", "type": "cat", "status": "available"}
  And method PUT
  Then status 200
  When path "/pets"
  And request {"name": "James", "type": "cat", "status": "available"}
  And method PUT
  Then status 200

Scenario: Place order
  When path "/orders"
  And request {"types": {"dog": 2}}
  And method POST
  Then status 200
