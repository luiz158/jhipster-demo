package org.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.domain.Tag;
import org.jhipster.repository.TagRepository;
import org.jhipster.web.rest.util.HeaderUtil;
import org.jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tag.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);
        
    @Inject
    private TagRepository tagRepository;
    
    /**
     * POST  /tags -> Create a new tag.
     */
    @RequestMapping(value = "/tags",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
        log.debug("REST request to save Tag : {}", tag);
        if (tag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tag", "idexists", "A new tag cannot already have an ID")).body(null);
        }
        Tag result = tagRepository.save(tag);
        return ResponseEntity.created(new URI("/api/tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tag", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tags -> Updates an existing tag.
     */
    @RequestMapping(value = "/tags",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tag> updateTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
        log.debug("REST request to update Tag : {}", tag);
        if (tag.getId() == null) {
            return createTag(tag);
        }
        Tag result = tagRepository.save(tag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tag", tag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tags -> get all the tags.
     */
    @RequestMapping(value = "/tags",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tag>> getAllTags(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tags");
        Page<Tag> page = tagRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tags/:id -> get the "id" tag.
     */
    @RequestMapping(value = "/tags/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        log.debug("REST request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        return Optional.ofNullable(tag)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tags/:id -> delete the "id" tag.
     */
    @RequestMapping(value = "/tags/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        log.debug("REST request to delete Tag : {}", id);
        tagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tag", id.toString())).build();
    }
}
