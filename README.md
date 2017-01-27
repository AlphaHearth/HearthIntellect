# HearthIntellect

[![Build Status](https://travis-ci.org/AlphaHearth/HearthIntellect.svg?branch=master)](https://travis-ci.org/AlphaHearth/HearthIntellect)

Hearthstone database website written in Java and pure [Spring](http://spring.io/).

## Future tasks

- [x] Deploy version `0.1`.
- [x] Migrate from Morphia to Spring Data.
- [x] Implement `UserController` with basic user authentication functionality.
- [x] Implement `MechanicController` and `PatchController`.
- [x] Implement admin controllers `CardAdminController`, `MechanicAdminController` and `PatchAdminController`.
- [ ] Refactor controller exceptions.
- [ ] Write complete test cases for all existing classes.
- [ ] Write complete documentation for all existing classes.
- [ ] Deploy version `0.2` on `HTTPS`.
- [ ] Write admin user interface.
- [ ] Use admin interface to populate `Mechanic` and `Patch` data.
- [ ] Update `Card` model to support Wild mode, Arena mode and Standard mode.
- [ ] Implement advanced filtering for Card browsing.
- [ ] Implement `DeckController` with basic deck browsing, creating and updating.
- [ ] Deploy version `0.3`.
- [ ] Implement locale support.
- [ ] Implement text searching on card name.
- [ ] Implement text searching on deck name.

## Technologies

- Spring MVC for RESTful API.
- Spring Data Mongo and MongoDB for Non-relational Data Persistence.