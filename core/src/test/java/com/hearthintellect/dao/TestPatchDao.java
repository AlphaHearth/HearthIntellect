package com.hearthintellect.dao;

import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.Patch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link PatchRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMongoConfig.class)
public class TestPatchDao {

    private static final String TEST_PATCH_ID = "8.0";
    private static final ZonedDateTime FUTURE_TIME = ZonedDateTime.now().plusYears(1000);

    @Autowired
    private PatchRepository patchRepository;

    @Before
    public void setUpTestPatch() {
        Patch aFuturePatch = new Patch(TEST_PATCH_ID, FUTURE_TIME,
                                       "We are happy to announce that Hearthstone 8.0 is released!"
                                        + " Card trading system is added to the game! Have fun, you guys!");

        patchRepository.insert(aFuturePatch);
    }

    @Test
    public void testPatchDaoInclude() {
        Patch patchInDB = patchRepository.include(FUTURE_TIME.plusDays(1));

        assertEquals(patchInDB.getId(), TEST_PATCH_ID);
    }

    @After
    public void tearDownTestPatch() {
        patchRepository.delete(new Patch(TEST_PATCH_ID, null, null));
    }

}
