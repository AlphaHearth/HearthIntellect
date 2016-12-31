package com.hearthintellect.repository;

import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.LocaleString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Basic CURD unit tests for {@link MechanicRepository}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public class MechanicRepositoryTest {
    private static final String TEST_ID = "TEST_MECHANIC";

    @Autowired
    private MechanicRepository mechanicRepository;

    private Mechanic testMechanic;

    @Before
    public void setUp() {
        testMechanic = new Mechanic();

        testMechanic.setMechanicId(TEST_ID);
        LocaleString mechanicName = new LocaleString();
        mechanicName.put(Locale.ENGLISH, "Battlecry");
        mechanicName.put(Locale.CHINESE, "战吼");
        testMechanic.setName(mechanicName);
        LocaleString mechanicDesc = new LocaleString();
        mechanicDesc.put(Locale.ENGLISH, "Do something nasty after you played the card");
        mechanicDesc.put(Locale.CHINESE, "当你打出这张卡时，触发效果");
        testMechanic.setDescription(mechanicDesc);

        mechanicRepository.save(testMechanic);
    }


    @Test
    public void testMechanicDaoInsertAndRead() {
        // The entity was inserted in `@Before` method
        Mechanic mechanic = mechanicRepository.findOne(TEST_ID);

        assertThat(mechanic.getMechanicId(), is(testMechanic.getMechanicId()));
        assertThat(mechanic.getName(), is(testMechanic.getName()));
        assertThat(mechanic.getDescription(), is(testMechanic.getDescription()));
    }

    @Test
    public void testMechanicDaoUpdate() {
        Mechanic mechanic = mechanicRepository.findOne(TEST_ID);

        LocaleString mechanicName = new LocaleString();
        mechanicName.put(Locale.ENGLISH, "Deathrattle");
        mechanicName.put(Locale.CHINESE, "亡语");
        mechanic.setName(mechanicName);
        LocaleString mechanicDesc = new LocaleString();
        mechanicDesc.put(Locale.ENGLISH, "Do something nasty after it dies");
        mechanicDesc.put(Locale.CHINESE, "当这张卡死亡时，触发效果");
        mechanic.setDescription(mechanicDesc);

        mechanicRepository.save(mechanic);
        mechanic = mechanicRepository.findOne(TEST_ID);

        assertThat(mechanic.getMechanicId(), is(testMechanic.getMechanicId()));
        assertThat(mechanic.getName(), not(is(testMechanic.getName())));
        assertThat(mechanic.getName(), is(mechanicName));
        assertThat(mechanic.getDescription(), not(is(testMechanic.getDescription())));
        assertThat(mechanic.getDescription(), is(mechanicDesc));
    }

    @Test
    public void testMechanicDaoDelete() {
        Mechanic mechanic = new Mechanic();
        mechanic.setMechanicId(TEST_ID);

        mechanicRepository.delete(mechanic);
        mechanic = mechanicRepository.findOne(TEST_ID);

        assertThat(mechanic, nullValue());
    }

}
