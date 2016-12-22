# HearthIntellect

Hearthstone database website written in Java.

## Future tasks

- [x] Update `crawler` module to support the latest [HearthHead](http://www.hearthhead.com/) web site.
- [ ] Add test cases for `CardRepository`, `PatchRepository` and `MechanicRepository`.
- [ ] Migrate from Jersey to SpringMVC.
- [ ] Fully implement `CardController`, `MechanicController` and `PatchController`.
- [ ] Add test cases for `CardController`, `MechanicController` and `PatchController`.
- [ ] Write web pages for `Card`, `Mechanic` and `Patch`.
- [ ] Deploy version `0.1`.
- [ ] Migrate `User` and `Deck` from Morphia and MongoDB to MyBatis and MySQL.
- [ ] Add controller classes for `User` and `Deck`.
- [ ] Release version `0.2`.
- [ ] Update `Card` model to support Wild mode, Arena mode and Standard mode.

## Technologies

- Spring for Dependency Injection
- SpringMVC and FreeMarker for Front End
- Spring Data, MyBatis and MySQL for Relational Data Persistence
- Morphia and MongoDB for Non-relational Data Persistence