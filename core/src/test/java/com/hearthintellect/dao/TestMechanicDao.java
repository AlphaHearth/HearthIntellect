package com.hearthintellect.dao;

import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.Mechanic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link MechanicRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMongoConfig.class)
public class TestMechanicDao {

    @Autowired
    private MechanicRepository mechanicRepository;

    @Test
    public void testMechanicDao() {
        Mechanic mechanic = new Mechanic();

        mechanic.setMechanicId(1);
        mechanic.setName("Battlecry");
        mechanic.setDescription("Do something nasty after you played the card");

        mechanicRepository.save(mechanic);
        mechanic = mechanicRepository.findById(1);

        assertEquals("Battlecry", mechanic.getName());
        assertEquals("Do something nasty after you played the card", mechanic.getDescription());

    }

}
