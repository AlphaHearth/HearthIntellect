package com.hearthintellect.exception;

/**
 * Specialized {@link NotFoundException} for {@link com.hearthintellect.model.Card Card}.
 */
public class CardNotFoundException extends NotFoundException {
    public CardNotFoundException(String cardId) {
        super("Card", cardId);
    }
}
