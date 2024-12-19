package in.tilaksalunke.restapi.controller;

import in.tilaksalunke.restapi.dto.ProfileDTO;
import in.tilaksalunke.restapi.io.ProfileRequest;
import in.tilaksalunke.restapi.io.ProfileResponse;
import in.tilaksalunke.restapi.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;

    /**
     * API endpoint to register new user
     * @param profileRequest
     * @return profileResponse
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest profileRequest){
        log.info("API /register is called {}", profileRequest);
        ProfileDTO profileDTO = mapToProfileDTO(profileRequest);
        profileDTO = profileService.createProfile(profileDTO);
        log.info("Printing the profile dto details {}", profileDTO);
        return mapToProfileResponse(profileDTO);
    }

    /**
     * Mapper method to map values from profile request to profile dto
     * @param profileRequest
     * @return profiledto
     * */

    private ProfileDTO mapToProfileDTO(ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }


    /**
     * Mapper method to map values from profile dto to profile response
     * @param profileDTO
     * @return profiledResponse
     * */
    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO){
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }
}
