package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.SalaryPackageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalaryPackage} and its DTO {@link SalaryPackageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalaryPackageMapper extends EntityMapper<SalaryPackageDTO, SalaryPackage> {



    default SalaryPackage fromId(String id) {
        if (id == null) {
            return null;
        }
        SalaryPackage salaryPackage = new SalaryPackage();

        return salaryPackage;
    }
}
