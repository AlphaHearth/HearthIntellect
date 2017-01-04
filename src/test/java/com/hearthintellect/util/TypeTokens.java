package com.hearthintellect.util;

import com.google.gson.reflect.TypeToken;
import com.hearthintellect.model.*;

import java.lang.reflect.Type;
import java.util.List;

public class TypeTokens {
    /** {@link Type} for {@link List} of {@link Card}s. */
    public static final Type cardListType = new TypeToken<List<Card>>(){}.getType();
    /** {@link Type} for {@link List} of {@link User}s. */
    public static final Type userListType = new TypeToken<List<User>>(){}.getType();
    /** {@link Type} for {@link List} of {@link Token}s. */
    public static final Type tokenListType = new TypeToken<List<Token>>(){}.getType();
    /** {@link Type} for {@link List} of {@link Mechanic}s. */
    public static final Type mechanicListType = new TypeToken<List<Mechanic>>(){}.getType();
    /** {@link Type} for {@link List} of {@link Patch}es. */
    public static final Type patchListType = new TypeToken<List<Patch>>(){}.getType();
}
