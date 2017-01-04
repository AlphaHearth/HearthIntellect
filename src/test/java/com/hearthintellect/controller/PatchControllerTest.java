package com.hearthintellect.controller;

import com.hearthintellect.model.Patch;
import com.hearthintellect.utils.Message;
import org.junit.Ignore;
import org.junit.Test;

public class PatchControllerTest extends ControllerTest {

    @Test
    @Ignore
    public void testGettingExistedPatch() throws Exception {
        Patch testPatch = testPatches.get(0);
        getWithAssertion("/patches/" + testPatch.getID(), 200, testPatch);
    }

    @Test
    @Ignore
    public void testGettingNotExistedPatch() throws Exception {
        int testPatchID = 123;
        Message expectedMessage = new Message(404, "Patch with given ID `" + testPatchID + "` does not exist.");
        getWithAssertion("/patches/" + testPatchID, 404, expectedMessage);
    }

}
