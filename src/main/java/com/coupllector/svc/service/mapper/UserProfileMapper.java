package com.coupllector.svc.service.mapper;

import com.coupllector.svc.domain.*;
import com.coupllector.svc.service.dto.UserProfileDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserProfile and its DTO UserProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface UserProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "streetAddress.id", target = "streetAddressId")
    @Mapping(source = "postalAddress.id", target = "postalAddressId")
    UserProfileDTO userProfileToUserProfileDTO(UserProfile userProfile);

    List<UserProfileDTO> userProfilesToUserProfileDTOs(List<UserProfile> userProfiles);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "streetAddressId", target = "streetAddress")
    @Mapping(source = "postalAddressId", target = "postalAddress")
    UserProfile userProfileDTOToUserProfile(UserProfileDTO userProfileDTO);

    List<UserProfile> userProfileDTOsToUserProfiles(List<UserProfileDTO> userProfileDTOs);

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
