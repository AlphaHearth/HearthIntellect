package com.hearthintellect.util;

import com.google.gson.reflect.TypeToken;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.Token;
import com.hearthintellect.model.User;

import java.lang.reflect.Type;
import java.util.List;

public class TypeTokens {
    /** {@link Type} for {@link List} of {@link Card}s. */
    public static final Type cardListType = new TypeToken<List<Card>>(){}.getType();
    /** {@link Type} for {@link List} of {@link User}s. */
    public static final Type userListType = new TypeToken<List<User>>(){}.getType();
    /** {@link Type} for {@link List} of {@link Token}s. */
    public static final Type tokenListType = new TypeToken<List<Token>>(){}.getType();
}
