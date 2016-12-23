# HearthIntellect

[![Build Status](https://travis-ci.org/AlphaHearth/HearthIntellect.svg?branch=master)](https://travis-ci.org/AlphaHearth/HearthIntellect)

Hearthstone database website written in Java.

## Future tasks

- [x] Update `crawler` module to support the latest [HearthHead](http://www.hearthhead.com/) web site.
- [x] Add test cases for `CardRepository` and `MechanicRepository`.
- [ ] Migrate from Jersey to SpringMVC.
- [ ] Implement `CardController`.
- [ ] Write home page and web page for `Card`.
- [ ] Deploy version `0.1`.
- [ ] Migrate `User` from Morphia and MongoDB to MyBatis and MySQL.
- [ ] Add controller classes for `User`.
- [ ] Implement user authentication and authorization framework.
- [ ] Add test cases for `PatchRepository`.
- [ ] Implement `MechanicController` and `PatchController`.
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
- Morphia and MongoDB for Non-relational Data Persistence