package com.hearthintellect.repository.morphia;

import com.hearthintellect.model.Patch;
import com.hearthintellect.repository.PatchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public class PatchRepositoryImpl extends MorphiaRepository<Integer, Patch> implements PatchRepository {
    @Override
    protected Class<Patch> getEntityClass() {
        return Patch.class;
    }

    @Override
    public Patch include(ZonedDateTime time) {
        return createQuery().field("releaseDate").lessThanOrEq(time).order("-releaseDate").get();
    }
}
