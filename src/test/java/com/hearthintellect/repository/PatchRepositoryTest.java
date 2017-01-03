package com.hearthintellect.repository;

import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.model.Patch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Basic CRUD test cases for {@link PatchRepository}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public class PatchRepositoryTest {

    private static final int TEST_BUILD_NUM = 1000000;
    private static final String TEST_PATCH_CODE = "200.0.0." + TEST_BUILD_NUM;
    private static final String TEST_PATCH_CODE_2 = "300.0.0." + TEST_BUILD_NUM;

    private @Autowired PatchRepository patchRepository;
    private Patch testPatch;

    @Before
    public void setUpTestPatch() {
        testPatch = new Patch(TEST_BUILD_NUM, TEST_PATCH_CODE);
        patchRepository.save(testPatch);
    }

    @Test
    public void testInsertAndRead() {
        // The entity was inserted in `@Before` method
        Patch patch = patchRepository.findOne(testPatch.getBuildNum());
        assertThat(patch, notNullValue());
        assertThat(patch.getBuildNum(), is(testPatch.getBuildNum()));
        assertThat(patch.getPatchCode(), is(testPatch.getPatchCode()));
    }

    @Test
    public void testUpdate() {
        testPatch.setPatchCode(TEST_PATCH_CODE_2);
        patchRepository.save(testPatch);
        Patch patch = patchRepository.findOne(testPatch.getBuildNum());
        assertThat(patch, notNullValue());
        assertThat(patch.getBuildNum(), is(testPatch.getBuildNum()));
        assertThat(patch.getPatchCode(), is(testPatch.getPatchCode()));
    }

    @Test
    public void testDelete() {
        patchRepository.delete(testPatch);
        Patch patch = patchRepository.findOne(testPatch.getBuildNum());
        assertThat(patch, nullValue());
    }

    @After
    public void tearDownTestPatch() {
        patchRepository.delete(new Patch(TEST_BUILD_NUM, ""));
    }

}
