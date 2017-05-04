# HearthIntellect

[![Build Status](https://travis-ci.org/AlphaHearth/HearthIntellect.svg?branch=master)](https://travis-ci.org/AlphaHearth/HearthIntellect)
[![codecov](https://codecov.io/gh/AlphaHearth/HearthIntellect/branch/master/graph/badge.svg)](https://codecov.io/gh/AlphaHearth/HearthIntellect)

Hearthstone database website written in Java.

## Future tasks

- [x] Deploy version `0.1`.
- [x] Implement `UserController` with basic user authentication functionality.
- [x] Implement `MechanicController` and `PatchController`.
- [x] Implement admin controllers `CardAdminController`, `MechanicAdminController` and `PatchAdminController`.
- [x] Deploy version `0.2`.
- [x] Refactor controller exceptions.
- [x] Test if creating duplicate `_id` documents is possible.
- [ ] Update `Card` model to support Gadgetzan's gang cards.
- [ ] Add tag to `Card` model for card banned in Arena. [link](http://www.hearthpwn.com/news/1709-upcoming-arena-changes-cards-being-removed-from)
- [ ] Update `crawler` and `Card` to support Un'goro.
- [ ] Implement advanced filtering for Card browsing.
- [ ] Crawl card and mechanic data again.
- [ ] Deploy version `0.3`.
- [ ] Perfect the current code while implementing Admin interface. (Fix all TODO comments, increase test coverage, optimize to make test faster, etc.)
- [ ] Deploy version `0.3.1`.
- [ ] Implement `DeckController` with basic deck browsing, creating and updating.
- [ ] Implement locale support.
- [ ] Implement text searching on card name.
- [ ] Implement text searching on deck name.

## Technologies

- Spring MVC for RESTful API.
- Spring Data Mongo and MongoDB for Non-relational Data Persistence.