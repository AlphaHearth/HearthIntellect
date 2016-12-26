package com.hearthintellect.dao;

import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.model.Patch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// TODO Update this test and PatchRepository
/**
 * Unit tests for {@link PatchRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public class PatchRepositoryTest {

    private static final int TEST_BUILD_NUM = 1000000;
    private static final String TEST_PATCH_CODE = "200.0.0." + TEST_BUILD_NUM;

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
