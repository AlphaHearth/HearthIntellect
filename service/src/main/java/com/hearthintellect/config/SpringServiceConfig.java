package com.hearthintellect.config;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.service.CardService;
import com.hearthintellect.service.MechanicService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration class for the {@code service} module.
 */
@Configuration
@Import(SpringCoreConfig.class)
public class SpringServiceConfig {

    @Bean
    public CardService cardService(CardRepository cardRepository) {
        CardService cardService = new CardService();
        cardService.setCardRepository(cardRepository);
        return cardService;
    }

    @Bean
    public MechanicService mechanicService(MechanicRepository mechanicRepository) {
        MechanicService mechanicService = new MechanicService();
        mechanicService.setMechanicRepository(mechanicRepository);
        return mechanicService;
    }

}
