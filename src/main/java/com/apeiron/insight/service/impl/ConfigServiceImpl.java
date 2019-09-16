package com.apeiron.insight.service.impl;

import com.apeiron.insight.service.ConfigService;
import com.apeiron.insight.domain.Config;
import com.apeiron.insight.repository.ConfigRepository;
import com.apeiron.insight.repository.search.ConfigSearchRepository;
import com.apeiron.insight.service.dto.ConfigDTO;
import com.apeiron.insight.service.mapper.ConfigMapper;
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
 * Service Implementation for managing {@link Config}.
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    private final ConfigRepository configRepository;

    private final ConfigMapper configMapper;

    private final ConfigSearchRepository configSearchRepository;

    public ConfigServiceImpl(ConfigRepository configRepository, ConfigMapper configMapper, ConfigSearchRepository configSearchRepository) {
        this.configRepository = configRepository;
        this.configMapper = configMapper;
        this.configSearchRepository = configSearchRepository;
    }

    /**
     * Save a config.
     *
     * @param configDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConfigDTO save(ConfigDTO configDTO) {
        log.debug("Request to save Config : {}", configDTO);
        Config config = configMapper.toEntity(configDTO);
        config.setId("DEFAULT_CONFIG");
        config = configRepository.save(config);
        ConfigDTO result = configMapper.toDto(config);
        return result;
    }



    /**
     * Get one config by id.
     *
     * @return the entity.
     */
    @Override
    public Optional<ConfigDTO> findOne() {
        log.debug("Request to get Config : {}", "DEFAULT_CONFIG");

        Optional<Config> default_config = configRepository.findById("DEFAULT_CONFIG");

        if(!default_config.isPresent()){
            Config config = new Config();
            config.setId("DEFAULT_CONFIG");
            configRepository.save(config);
        }

        return configRepository.findById("DEFAULT_CONFIG")
            .map(configMapper::toDto);
    }

    /**
     * Delete the config by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Config : {}", id);
        configRepository.deleteById(id);
        configSearchRepository.deleteById(id);
    }

}
