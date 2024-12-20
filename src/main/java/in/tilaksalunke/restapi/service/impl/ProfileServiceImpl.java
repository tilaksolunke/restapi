package in.tilaksalunke.restapi.service.impl;

import in.tilaksalunke.restapi.dto.ProfileDTO;
import in.tilaksalunke.restapi.entity.ProfileEntity;
import in.tilaksalunke.restapi.exceptions.ItemExistsException;
import in.tilaksalunke.restapi.repository.ProfileRepository;
import in.tilaksalunke.restapi.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder encoder;

    /**
     * It will save the user details to database
     * @param profileDTO
     * @return profiledto
     * */
    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        if(profileRepository.existsByEmail(profileDTO.getEmail())){
            throw new ItemExistsException("Profile already exists " + profileDTO.getEmail());
        }
        profileDTO.setPassword(encoder.encode(profileDTO.getPassword()));
        ProfileEntity profileEntity = mapToProfileEntity(profileDTO);
        profileEntity.setProfileId(UUID.randomUUID().toString());
        profileEntity = profileRepository.save(profileEntity);
        log.info("Printing the profile entity details {}", profileEntity);
        return mapToProfileDTO(profileEntity);
    }

    /**
     * Mapper method to map values from profile entity to profile dto
     * @param profileEntity
     * @return profiledDto
     * */

    private ProfileDTO mapToProfileDTO(ProfileEntity profileEntity) {
        return modelMapper.map(profileEntity, ProfileDTO.class);
    }

    /**
     * Mapper method to map values from profile dto to profile entity
     * @param profileDTO
     * @return profiledEntity
     * */

    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileEntity.class);
    }
}
