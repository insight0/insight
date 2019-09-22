package com.apeiron.insight.service;

import com.apeiron.insight.domain.Skill;
import com.apeiron.insight.repository.SkillRepository;
import com.apeiron.insight.repository.search.SkillSearchRepository;
import com.apeiron.insight.service.dto.SkillDTO;
import com.apeiron.insight.service.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    private final SkillSearchRepository skillSearchRepository;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper, SkillSearchRepository skillSearchRepository) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
        this.skillSearchRepository = skillSearchRepository;
    }

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);
        Skill skill = skillMapper.toEntity(skillDTO);
        if(skill.getName() != null){
            skill.setName(skill.getName().toLowerCase().trim());
        }

        Skill old = findByName(skill.getName());

        if(old != null){
            skillDTO.setId(old.getId());
            return skillDTO;
        }

        skill = skillRepository.save(skill);
        SkillDTO result = skillMapper.toDto(skill);
        skillSearchRepository.save(skill);
        return result;
    }

    public Skill findByName(String name) {
        log.debug("Request to get Skill : {}", name);
        return skillRepository.findByName(name);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    public List<SkillDTO> findAll() {
        log.debug("Request to get all Skills");
        return skillRepository.findAll().stream()
            .map(skillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one skill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SkillDTO> findOne(String id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id)
            .map(skillMapper::toDto);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
        skillSearchRepository.deleteById(id);
    }

    /**
     * Search for the skill corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    public List<SkillDTO> search(String query) {
        log.debug("Request to search Skills for query {}", query);
        return StreamSupport
            .stream(skillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(skillMapper::toDto)
            .collect(Collectors.toList());
    }
}
