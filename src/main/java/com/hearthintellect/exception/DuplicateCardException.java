package com.hearthintellect.exception;

public class DuplicateCardException extends DuplicateEntityException {
    public DuplicateCardException(String cardId) {
        super("Card", cardId);
    }
}
