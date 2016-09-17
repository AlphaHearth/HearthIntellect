package com.hearthintellect.dao;

import com.hearthintellect.config.SpringCoreTestConfig;
import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.LocaleString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Basic CURD unit tests for {@link MechanicRepository}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfig.class)
public class TestMechanicDao {
    private static final int TEST_ID = 100000;

    @Autowired
    private MechanicRepository mechanicRepository;

    @Test
    public void testMechanicDaoInsert() {
        Mechanic mechanic = new Mechanic();

        mechanic.setMechanicId(TEST_ID);
        LocaleString mechanicName = new LocaleString();
        mechanicName.put(Locale.ENGLISH, "Battlecry");
        mechanicName.put(Locale.CHINESE, "战吼");
        mechanic.setName(mechanicName);
        LocaleString mechanicDesc = new LocaleString();
        mechanicDesc.put(Locale.ENGLISH, "Do something nasty after you played the card");
        mechanicDesc.put(Locale.CHINESE, "当你打出这张卡时，触发效果");
        mechanic.setDescription(mechanicDesc);

        mechanicRepository.insert(mechanic);
        mechanic = mechanicRepository.findById(TEST_ID);

        assertEquals(mechanicName, mechanic.getName());
        assertEquals(mechanicDesc, mechanic.getDescription());

        testMechanicDaoUpdate();
    }

    /** Invoked at the end of {@link #testMechanicDaoInsert()} */
    public void testMechanicDaoUpdate() {
        Mechanic mechanic = mechanicRepository.findById(TEST_ID);

        LocaleString mechanicName = new LocaleString();
        mechanicName.put(Locale.ENGLISH, "Deathrattle");
        mechanicName.put(Locale.CHINESE, "亡语");
        mechanic.setName(mechanicName);
        LocaleString mechanicDesc = new LocaleString();
        mechanicDesc.put(Locale.ENGLISH, "Do something nasty after it dies");
        mechanicDesc.put(Locale.CHINESE, "当这张卡死亡时，触发效果");
        mechanic.setDescription(mechanicDesc);

        mechanicRepository.update(mechanic);

        mechanic = mechanicRepository.findById(TEST_ID);

        assertEquals(mechanicName, mechanic.getName());
        assertEquals(mechanicDesc, mechanic.getDescription());

        testMechanicDaoDelete();
    }

    @Test
    public void testMechanicDaoDelete() {
        Mechanic mechanic = new Mechanic();
        mechanic.setMechanicId(TEST_ID);

        mechanicRepository.delete(mechanic);
        mechanic = mechanicRepository.findById(TEST_ID);

        assertNull(mechanic);
    }

}
