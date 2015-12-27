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

    @Autowired
    private MechanicRepository mechanicRepository;

    @Test
    public void testMechanicDaoInsert() {
        Mechanic mechanic = new Mechanic();

        mechanic.setMechanicId(1);
        mechanic.setName("Battlecry");
        mechanic.setDescription("Do something nasty after you played the card");

        mechanicRepository.insert(mechanic);
        mechanic = mechanicRepository.findById(1);

        assertEquals("Battlecry", mechanic.getName());
        assertEquals("Do something nasty after you played the card", mechanic.getDescription());
    }

    public void testMechanicDaoUpdate() {
        Mechanic mechanic = new Mechanic();

        mechanic.setMechanicId(1);
        mechanic.setName("Deathrattle");
        mechanic.setDescription("Do something nasty after it dies");

        mechanicRepository.insert(mechanic);

        mechanic.setDescription("Do something after it dies");
        mechanicRepository.update(mechanic);

        mechanic = mechanicRepository.findById(1);

        assertEquals("Deathrattle", mechanic.getName());
        assertEquals("Do something after it dies", mechanic.getDescription());

        testMechanicDaoDelete();
    }

    public void testMechanicDaoDelete() {
        Mechanic mechanic = new Mechanic();
        mechanic.setMechanicId(1);

        mechanicRepository.delete(mechanic);
        mechanic = mechanicRepository.findById(1);

        assertNull(mechanic);
    }

}
