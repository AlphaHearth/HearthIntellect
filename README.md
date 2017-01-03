# HearthIntellect

[![Build Status](https://travis-ci.org/AlphaHearth/HearthIntellect.svg?branch=master)](https://travis-ci.org/AlphaHearth/HearthIntellect)

Hearthstone database website written in Java and pure [Spring](http://spring.io/).

## Future tasks

- [x] Deploy version `0.1`.
- [x] Migrate from Morphia to Spring Data.
- [x] Implement `UserController` with basic user authentication functionality.
- [ ] Implement `MechanicController` and `PatchController`.
- [ ] Implement admin controllers `CardAdminController`, `MechanicAdminController` and `PatchAdminController`.
- [ ] Write admin interfaces using FreeMarker.
- [ ] Write integration test cases.
- [ ] Write complete test cases for all existing classes.
- [ ] Write complete documentation for all existing classes.
- [ ] Deploy version `0.2`.
- [ ] Use admin interface to populate `Mechanic` and `Patch` data.
- [ ] Implement advanced filtering for Card browsing.
- [ ] Update `Card` model to support Wild mode, Arena mode and Standard mode.
- [ ] Add controller classes for `Deck`.
- [ ] Deploy version `0.3`.
- [ ] Implement text searching on card name.
- [ ] Implement text searching on deck name.

## Technologies

- Spring Core for Dependency Injection.
- Spring MVC for RESTful API.
- Spring Data Mongo and MongoDB for Non-relational Data Persistence.