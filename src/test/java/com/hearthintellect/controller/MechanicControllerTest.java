package com.hearthintellect.controller;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.Message;
import org.junit.Test;

public class MechanicControllerTest extends ControllerTest {

    @Test
    public void testMechanicListing() throws Exception {
        // `MechanicController` should return all mechanics, sorted by mechanic ID in ascending order
        getWithAssertion("/mechanics", 200, testMechanics);
    }

    @Test
    public void testGettingExistedMechanic() throws Exception {
        Mechanic testMechanic = testMechanics.get(0);
        getWithAssertion("/mechanics/" + testMechanic.getMechanicId(), 200, testMechanic);
    }

    @Test
    public void testGettingNotExistedMechanic() throws Exception {
        String testMechanicID = "NOT_REALLY_EXIST";
        Message expectedMessage = entityNotFoundMessage("Mechanic", testMechanicID);
        getWithAssertion("/mechanics/" + testMechanicID, 404, expectedMessage);
    }
}
