# HearthIntellect

[![Build Status](https://travis-ci.org/AlphaHearth/HearthIntellect.svg?branch=master)](https://travis-ci.org/AlphaHearth/HearthIntellect)

Hearthstone database website written in Java and pure [Spring](http://spring.io/).

## Future tasks

- [x] Update `crawler` module to support the latest [HearthHead](http://www.hearthhead.com/) web site.
- [x] Add test cases for `CardRepository` and `MechanicRepository`.
- [x] Migrate from Jersey to SpringMVC.
- [x] Deploy version `0.1`.
- [ ] Migrate from Morphia to Spring Data.
- [ ] Add locale support.
- [ ] Deploy version `0.1.1`.
- [ ] Implement advanced filtering for Card browsing.
- [ ] Deploy version `0.1.2`.
- [ ] Migrate `User` from MongoDB to MyBatis and MySQL using Spring Data and Spring Transaction.
- [ ] Add controller classes for `User`.
- [ ] Add test cases for `PatchRepository`.
- [ ] Implement `MechanicController` and `PatchController`.
- [ ] Implement user authentication and authorization framework using Spring Security.
- [ ] Implement Admin interfaces.
- [ ] Use Admin interface to populate `Mechanic` and `Patch` data.
- [ ] Release version `0.2`.
- [ ] Update `Card` model to support Wild mode, Arena mode and Standard mode.
- [ ] Migrate `Deck` from Morphia and MongoDB to MyBatis and MySQL.
- [ ] Add controller classes for `Deck`.

## Technologies

- Spring for Dependency Injection
- SpringMVC and FreeMarker for Front End
- Spring Data, MyBatis and MySQL for Relational Data Persistence
- Spring Data, MongoDB Java Driver and MongoDB for Non-relational Data Persistence