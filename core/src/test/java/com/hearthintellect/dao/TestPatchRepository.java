package com.hearthintellect.dao;

import com.hearthintellect.config.SpringCoreTestConfig;
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

// TODO Update this test and PatchRepository
/**
 * Unit tests for {@link PatchRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfig.class)
public class TestPatchRepository {

    private static final int TEST_BUILD_NUM = 1000000;
    private static final String TEST_PATCH_CODE = "8.0";

    @Autowired
    private PatchRepository patchRepository;

    @Before
    public void setUpTestPatch() {
        Patch aFuturePatch = new Patch(TEST_BUILD_NUM, TEST_PATCH_CODE);

        patchRepository.insert(aFuturePatch);
    }

    @Test
    public void testPatchDaoInclude() {
        // Patch patchInDB = patchRepository.include(FUTURE_TIME.plusDays(1));

        // assertEquals(patchInDB.getId(), TEST_PATCH_ID);
    }

    @After
    public void tearDownTestPatch() {
        patchRepository.delete(new Patch(TEST_BUILD_NUM, ""));
    }

}
