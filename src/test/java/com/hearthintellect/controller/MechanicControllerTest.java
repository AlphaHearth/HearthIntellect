package com.hearthintellect.controller;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.Message;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class MechanicControllerTest extends ControllerTest {

    @Test
    @Ignore
    public void testMechanicListing() throws Exception {
        // By default, `MechanicController` should return all mechanics, ascendantly sorted by mechanic ID
        getWithAssertion("/mechanics", 200, testMechanics);
    }

    @Test
    @Ignore
    public void testMechanicListingWithParameters() throws Exception {
        // Mechanic listing functionality should also be able to accept optional query string parameters
        // `pageNum` and `pageSize`, which defines the page of mechanics should be returned
        int pageNum = 1;
        int pageSize = 5; // The 2nd page with 5 mechanics
        List<Mechanic> expectedMechanics = testMechanics.subList(4, 9);
        getWithAssertion("/mechanics?pageSize=" + pageSize + "&pageNum=" + pageNum, 200, expectedMechanics);
    }

    @Test
    @Ignore
    public void testGettingExistedMechanic() throws Exception {
        Mechanic testMechanic = testMechanics.get(0);
        getWithAssertion("/mechanics/" + testMechanic.getMechanicId(), 200, testMechanic);
    }

    @Test
    @Ignore
    public void testGettingNotExistedMechanic() throws Exception {
        String testMechanicID = "NOT_REALLY_EXIST";
        Message expectedMessage = new Message(404, "Mechanic with given ID `" + testMechanicID + "` does not exist.");
        getWithAssertion("/mechanics/" + testMechanicID, 404, expectedMessage);
    }
}
