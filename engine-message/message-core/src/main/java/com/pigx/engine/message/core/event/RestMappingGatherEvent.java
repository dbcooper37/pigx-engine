package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.RestMapping;

import java.time.Clock;
import java.util.List;


public class RestMappingGatherEvent extends AbstractApplicationEvent<List<RestMapping>> {

    public RestMappingGatherEvent(List<RestMapping> data) {
        super(data);
    }

    public RestMappingGatherEvent(List<RestMapping> data, Clock clock) {
        super(data, clock);
    }
}
