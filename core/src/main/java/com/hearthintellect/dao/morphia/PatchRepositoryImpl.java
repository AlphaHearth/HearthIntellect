package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.PatchRepository;
import com.hearthintellect.model.Patch;

import java.time.ZonedDateTime;

public class PatchRepositoryImpl extends MorphiaRepository<Patch> implements PatchRepository {
    @Override
    protected Class<Patch> getEntityClass() {
        return Patch.class;
    }

    @Override
    public Patch include(ZonedDateTime time) {
        return createQuery().field("releaseDate").lessThanOrEq(time).order("-releaseDate").get();
    }
}
