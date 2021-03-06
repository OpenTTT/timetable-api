package org.openttt.rest;

import org.openttt.model.Tag;
import org.openttt.repo.TagRepo;
import org.openttt.rest.dto.MapperService;
import org.openttt.rest.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagRepo tagRepo;

    @Autowired
    private MapperService mapper;

    @GetMapping
    public List<TagDTO> getAllTags() {
        return mapper.mapAll(tagRepo.findAll(), TagDTO.class);
    }

    @PostMapping
    public TagDTO createTag(@Valid @RequestBody TagDTO tag) {
        Tag incomingTag = mapper.map(tag, Tag.class);
        Tag savedTag = tagRepo.save(incomingTag);
        return mapper.map(savedTag, TagDTO.class);
    }
}
