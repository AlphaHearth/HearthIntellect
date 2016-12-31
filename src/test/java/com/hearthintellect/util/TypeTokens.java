package com.hearthintellect.util;

import com.google.gson.reflect.TypeToken;
import com.hearthintellect.model.Card;

import java.lang.reflect.Type;
import java.util.List;

public class TypeTokens {
    public static final Type cardListType = new TypeToken<List<Card>>(){}.getType();
}
