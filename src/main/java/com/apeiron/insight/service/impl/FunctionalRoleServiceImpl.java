package com.apeiron.insight.service.impl;

import com.apeiron.insight.service.FunctionalRoleService;
import com.apeiron.insight.domain.FunctionalRole;
import com.apeiron.insight.repository.FunctionalRoleRepository;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;
import com.apeiron.insight.service.mapper.FunctionalRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FunctionalRole}.
 */
@Service
public class FunctionalRoleServiceImpl implements FunctionalRoleService {

    private final Logger log = LoggerFactory.getLogger(FunctionalRoleServiceImpl.class);

    private final FunctionalRoleRepository functionalRoleRepository;

    private final FunctionalRoleMapper functionalRoleMapper;


    public FunctionalRoleServiceImpl(FunctionalRoleRepository functionalRoleRepository, FunctionalRoleMapper functionalRoleMapper) {
        this.functionalRoleRepository = functionalRoleRepository;
        this.functionalRoleMapper = functionalRoleMapper;
    }

    /**
     * Save a functionalRole.
     *
     * @param functionalRoleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FunctionalRoleDTO save(FunctionalRoleDTO functionalRoleDTO) {
        log.debug("Request to save FunctionalRole : {}", functionalRoleDTO);
        FunctionalRole functionalRole = functionalRoleMapper.toEntity(functionalRoleDTO);
        functionalRole = functionalRoleRepository.save(functionalRole);
        FunctionalRoleDTO result = functionalRoleMapper.toDto(functionalRole);
        return result;
    }

    /**
     * Get all the functionalRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<FunctionalRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FunctionalRoles");
        return functionalRoleRepository.findAll(pageable)
            .map(functionalRoleMapper::toDto);
    }


    /**
     * Get one functionalRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<FunctionalRoleDTO> findOne(String id) {
        log.debug("Request to get FunctionalRole : {}", id);
        return functionalRoleRepository.findById(id)
            .map(functionalRoleMapper::toDto);
    }

    /**
     * Delete the functionalRole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete FunctionalRole : {}", id);
        functionalRoleRepository.deleteById(id);
    }

    /**
     * Get functionalRole's titles by id.
     *
     * @return the List of titles .
     */

    @Override
    public List<String> findRoleTitles() {
        List<FunctionalRole> roles = functionalRoleRepository.findAll();
        List<String> titles = new ArrayList<>();
        for (FunctionalRole role : roles
        ) {
            titles.add(role.getName());
        }
        return titles;
    }

}
