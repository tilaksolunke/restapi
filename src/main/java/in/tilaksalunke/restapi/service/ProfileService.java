package in.tilaksalunke.restapi.service;

import in.tilaksalunke.restapi.dto.ProfileDTO;

public interface ProfileService {

    /**
     * It will save the user details to database
     * @param profileDTO
     * @return profiledto
     * */
    ProfileDTO createProfile(ProfileDTO profileDTO);
}
