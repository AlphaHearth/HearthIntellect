package com.hearthintellect.controller.exception;

/**
 * Specialized {@link EntityNotFoundException} for {@link com.hearthintellect.model.Card Card}.
 */
public class CardNotFoundException extends EntityNotFoundException {
    public CardNotFoundException(String entityId) {
        super("Card", entityId);
    }
}
