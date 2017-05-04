package com.hearthintellect.exception;

public abstract class Exceptions {
    private static final String ENTITY_NOT_FOUND_MESSAGE = "%s with ID `%s` does not exist.";
    private static final String DUPLICATE_ENTITY_MESSAGE = "%s with ID `%s` already exists.";
    private static final String EMPTY_TOKEN_MESSAGE = "Token must be provided to use this API.";
    private static final String INVALID_CREDENTIAL_MESSAGE = "The provided username or password is invalid.";
    private static final String INVALID_OR_EXPIRED_TOKEN_MESSAGE = "The given token `%s` is invalid for this API or has expired.";

    /* Public exceptions */

    public static ErrorResponseException cardNotFoundException(String cardID) {
        return entityNotFoundException("Card", cardID);
    }

    public static ErrorResponseException duplicateCardException(String cardID) {
        return duplicateEntityException("Card", cardID);
    }

    public static ErrorResponseException mechanicNotFoundException(String mechanicID) {
        return entityNotFoundException("Mechanic", mechanicID);
    }

    public static ErrorResponseException duplicateMechanicException(String mechanicID) {
        return duplicateEntityException("Mechanic", mechanicID);
    }

    public static ErrorResponseException patchNotFoundException(int patchBuildNum) {
        return entityNotFoundException("Patch", String.valueOf(patchBuildNum));
    }

    public static ErrorResponseException duplicatePatchException(int patchBuildNum) {
        return duplicateEntityException("Patch", String.valueOf(patchBuildNum));
    }

    public static ErrorResponseException userNotFoundException(String username) {
        return entityNotFoundException("User", username);
    }

    public static ErrorResponseException duplicateUserException(String username) {
        return duplicateEntityException("User", username);
    }

    public static ErrorResponseException tokenNotFoundException(String token) {
        return entityNotFoundException("Token", token);
    }

    public static ErrorResponseException emptyTokenException() {
        return badRequestException(EMPTY_TOKEN_MESSAGE);
    }

    public static ErrorResponseException invalidCredentialException() {
        return unauthorizedException(INVALID_CREDENTIAL_MESSAGE);
    }

    public static ErrorResponseException invalidOrExpiredTokenException(String token) {
        return unauthorizedException(String.format(INVALID_OR_EXPIRED_TOKEN_MESSAGE, token));
    }

    /* Private methods */

    public static ErrorResponseException badRequestException(String message) {
        return errorResponseException(400, message);
    }

    private static ErrorResponseException unauthorizedException(String message) {
        return errorResponseException(401, message);
    }

    private static ErrorResponseException forbiddenException(String message) {
        return errorResponseException(403, message);
    }

    private static ErrorResponseException duplicateEntityException(String entityName, String entityID) {
        return forbiddenException(String.format(DUPLICATE_ENTITY_MESSAGE, entityName, entityID));
    }

    private static ErrorResponseException entityNotFoundException(String entityName, String entityID) {
        return errorResponseException(404, String.format(ENTITY_NOT_FOUND_MESSAGE, entityName, entityID));
    }

    private static ErrorResponseException errorResponseException(int statusCode, String message) {
        return new ErrorResponseException(statusCode, message);
    }

    private Exceptions() {
        throw new AssertionError("This class should not be instantiated.");
    }
}
