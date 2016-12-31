package com.hearthintellect.util;

import com.google.gson.JsonObject;
import org.bson.Document;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class MongoMatchers {

    public static Matcher<Document> index(IndexField... indexFields) {
        return new IndexMatcher(indexFields);
    }

    public static IndexField field(String field) {
        return new IndexField(field, true);
    }

    public static IndexField field(String field, boolean isAscend) {
        return new IndexField(field, isAscend);
    }

    public static class IndexField {
        private final String field;
        private final int ascendence;

        private IndexField(String field, boolean isAscend) {
            this.field = field;
            this.ascendence = isAscend ? 1 : -1;
        }
    }

    private static class IndexMatcher extends BaseMatcher<Document> {
        private final IndexField[] fields;

        public IndexMatcher(IndexField[] fields) {
            this.fields = fields;
        }

        @Override
        public boolean matches(Object item) {
            try {
                Document document = (Document) item;
                Document keys = document.get("key", Document.class);
                if (keys.size() != fields.length)
                    return false;
                for (IndexField field : fields) {
                    Integer ascendance = keys.getInteger(field.field);
                    if (ascendance == null || !ascendance.equals(field.ascendence))
                        return false;
                }
                return true;
            } catch (Throwable ex) {
                return false;
            }
        }

        @Override
        public void describeTo(Description description) {
            JsonObject jsonObject = new JsonObject();
            for (IndexField field : fields)
                jsonObject.addProperty(field.field, field.ascendence);
            description.appendText(jsonObject.toString());
        }
    }
}
