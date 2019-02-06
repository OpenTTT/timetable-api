package org.openttd.opentttimetables.rest.dto;

import org.modelmapper.ModelMapper;
import org.openttd.opentttimetables.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperService {
    @Autowired
    private ModelMapper mapper;

    public <S, T> T map(S source, Class<T> targetClass) {
        return mapper.map(source, targetClass);
    }

    public <S, T> List<T> mapAll(Iterable<S> sources, Class<T> targetClass) {
        return Streams.stream(sources)
                .map(s -> mapper.map(s, targetClass))
                .collect(Collectors.toList());
    }
}
