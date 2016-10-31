package com.hearthintellect.dao;

import com.hearthintellect.model.Patch;

import java.time.ZonedDateTime;

public interface PatchRepository extends Repository<Integer, Patch> {

    /**
     * Given a specific time point, finds out which {@link Patch} is it within.
     *
     * @param time the given time point
     * @return the {@link Patch} include the time point
     */
    Patch include(ZonedDateTime time);

}
