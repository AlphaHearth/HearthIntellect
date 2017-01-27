package com.hearthintellect.controller;

import com.hearthintellect.model.Mechanic;
import com.hearthintellect.model.Token;
import com.hearthintellect.util.ResourceUtils;
import com.hearthintellect.utils.LocaleString;
import com.hearthintellect.utils.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class MechanicAdminControllerTest extends ControllerTest {

    private Mechanic newMechanic;

    @Before
    public void setUp() {
        super.setUp();
        newMechanic = ResourceUtils.readResrouceJsonAsEntity("new_mechanic.json", Mechanic.class, gson);
    }

    @Test
    public void testCreatingMechanicWithoutToken() {
        postWithAssertion("/mechanics", newMechanic, 400, emptyTokenMessage());
    }

    @Test
    public void testCreatingMechanicWithNonAdminToken() {
        Token testToken = testTokens.get(0);
        Message expectedMessage = invalidTokenMessage(testToken.getID());
        postWithAssertion("/mechanics?token=" + testToken.getID(), newMechanic, 401, expectedMessage);
    }

    @Test
    public void testCreatingExistedMechanic() {
        Mechanic testMechanic = testMechanics.get(0);
        Message expectedMessage = duplicateEntityMessage("Mechanic", testMechanic.getID());
        postWithAssertion("/mechanics?token=" + adminTokenID, testMechanic, 403, expectedMessage);
    }

    @Test
    public void testCreatingNonExistedMechanic() {
        Message expectedMessage = entityCreatedMessage("Mechanic", newMechanic.getID(), "/mechanics/" + newMechanic.getID());
        postWithAssertion("/mechanics?token=" + adminTokenID, newMechanic, 201, expectedMessage);
        getWithAssertion("/mechanics/" + newMechanic.getID(), 200, newMechanic);
    }

    @Test
    public void testDeletingMechanicWithoutToken() {
        Mechanic testMechanic = testMechanics.get(0);
        deleteWithAssertion("/mechanics/" + testMechanic.getID(), 400, emptyTokenMessage());
    }

    @Test
    public void testDeletingMechanicWithNonAdminToken() {
        Mechanic testMechanic = testMechanics.get(0);
        Token testToken = testTokens.get(0);
        Message expectedMessage = invalidTokenMessage(testToken.getID());
        deleteWithAssertion("/mechanics/" + testMechanic.getID() + "?token=" + testToken.getID(), 401, expectedMessage);
    }

    @Test
    public void testDeletingNonExistedMechanic() {
        Message expectedMessage = entityNotFoundMessage("Mechanic", newMechanic.getID());
        deleteWithAssertion("/mechanics/" + newMechanic.getID() + "?token=" + adminTokenID, 404, expectedMessage);
    }

    @Test
    public void testDeletingExistedMechanic() {
        Mechanic testMechanic = testMechanics.get(0);
        deleteWithAssertion("/mechanics/" + testMechanic.getID() + "?token=" + adminTokenID, 204, null);
        getWithAssertion("/mechanics/" + testMechanic.getID(), 404, null);
    }

    @Test
    public void testUpdatingMechanicWithoutToken() {
        Mechanic testMechanic = testMechanics.get(0);
        putWithAssertion("/mechanics/" + testMechanic.getID(), testMechanic, 400, emptyTokenMessage());
    }

    @Test
    public void testUpdatingMechanicWithNonAdminToken() {
        Mechanic testMechanic = testMechanics.get(0);
        Token testToken = testTokens.get(0);
        Message expectedMessage = invalidTokenMessage(testToken.getID());
        putWithAssertion("/mechanics/" + testMechanic.getID() + "?token=" + testToken.getID(), testMechanic, 401, expectedMessage);
    }

    @Test
    public void testUpdatingNonExistedMechanic() {
        Message expectedMessage = entityNotFoundMessage("Mechanic", newMechanic.getID());
        putWithAssertion("/mechanics/" + newMechanic.getID() + "?token=" + adminTokenID, newMechanic, 404, expectedMessage);
    }

    @Test
    public void testUpdatingExistedMechanic() {
        Mechanic testMechanic = testMechanics.get(0);
        Mechanic sentMechanicBody = new Mechanic();
        sentMechanicBody.setID(testMechanic.getID());
        sentMechanicBody.setName(new LocaleString());
        sentMechanicBody.getName().put(Locale.US, "Something strange.");
        testMechanic.getName().put(Locale.US, "Something strange.");
        sentMechanicBody.getName().put(Locale.CHINA, "");
        testMechanic.getName().put(Locale.CHINA, "");

        Message expectedMessage = entityUpdatedMessage("Mechanic", testMechanic.getID(), "/mechanics/" + testMechanic.getID());
        putWithAssertion("/mechanics/" + testMechanic.getID() + "?token=" + adminTokenID, sentMechanicBody, 201, expectedMessage);
        getWithAssertion("/mechanics/" + testMechanic.getID(), 200, testMechanic);
    }
}
