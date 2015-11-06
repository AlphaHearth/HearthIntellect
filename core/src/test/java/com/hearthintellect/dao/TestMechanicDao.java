package com.hearthintellect.dao;

import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.Mechanic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for {@link MechanicRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMongoConfig.class)
public class TestMechanicDao {
    private static final int TEST_ID = 100000;

    @Autowired
    private MechanicRepository mechanicRepository;

    @Test
    public void testMechanicDaoInsert() {
        Mechanic mechanic = new Mechanic();

        mechanic.setMechanicId(TEST_ID);
        mechanic.setName("Battlecry");
        mechanic.setDescription("Do something nasty after you played the card");

        mechanicRepository.save(mechanic);
        mechanic = mechanicRepository.findById(TEST_ID);

        assertEquals("Battlecry", mechanic.getName());
        assertEquals("Do something nasty after you played the card", mechanic.getDescription());
    }

    @Test
    public void testMechanicDaoUpdate() {
        Mechanic mechanic = new Mechanic();

        mechanic.setMechanicId(TEST_ID);
        mechanic.setName("Deathrattle");
        mechanic.setDescription("Do something nasty after it dies");

        mechanicRepository.save(mechanic);

        mechanic.setDescription("Do something after it dies");
        mechanicRepository.update(mechanic);

        mechanic = mechanicRepository.findById(TEST_ID);

        assertEquals("Deathrattle", mechanic.getName());
        assertEquals("Do something after it dies", mechanic.getDescription());
    }

    @Test
    public void testMechanicDaoRemove() {
        Mechanic mechanic = new Mechanic();
        mechanic.setMechanicId(TEST_ID);

        mechanicRepository.remove(mechanic);
        mechanic = mechanicRepository.findById(TEST_ID);

        assertNull(mechanic);
    }

}
