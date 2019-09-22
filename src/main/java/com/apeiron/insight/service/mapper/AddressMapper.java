package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.AddressDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Service
public class AddressMapper {


    public Address toEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();

        address.setLine1(dto.getLine1());
        address.setLine2(dto.getLine2());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setLtd(dto.getLtd());
        address.setLgt(dto.getLgt());

        return address;
    }


    public AddressDTO toDto(Address entity) {
        if (entity == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setLine1(entity.getLine1());
        addressDTO.setLine2(entity.getLine2());
        addressDTO.setCity(entity.getCity());
        addressDTO.setPostalCode(entity.getPostalCode());
        addressDTO.setCountry(entity.getCountry());
        addressDTO.setLtd(entity.getLtd());
        addressDTO.setLgt(entity.getLgt());

        return addressDTO;
    }


    public List<Address> toEntity(List<AddressDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Address> list = new ArrayList<Address>(dtoList.size());
        for (AddressDTO addressDTO : dtoList) {
            list.add(toEntity(addressDTO));
        }

        return list;
    }

    public List<AddressDTO> toDto(List<Address> entityList) {
        if (entityList == null) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>(entityList.size());
        for (Address address : entityList) {
            list.add(toDto(address));
        }

        return list;
    }

    private Address fromId(String id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        return address;
    }
}
