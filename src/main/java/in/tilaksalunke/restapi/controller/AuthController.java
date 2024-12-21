package in.tilaksalunke.restapi.controller;

import in.tilaksalunke.restapi.dto.ProfileDTO;
import in.tilaksalunke.restapi.io.AuthRequest;
import in.tilaksalunke.restapi.io.AuthResponse;
import in.tilaksalunke.restapi.io.ProfileRequest;
import in.tilaksalunke.restapi.io.ProfileResponse;
import in.tilaksalunke.restapi.service.CustomerUserDetailsService;
import in.tilaksalunke.restapi.service.ProfileService;
import in.tilaksalunke.restapi.service.TokenBlacklistService;
import in.tilaksalunke.restapi.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor

public class AuthController {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenBlacklistService tokenBlacklistService;

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

    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("API /login is called {}", authRequest);
        authenticate(authRequest);
        final UserDetails userDetails = userDetailsService.loadUserByUsername((authRequest.getEmail()));
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, authRequest.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/signout")
    public void signout(HttpServletRequest request){
        String jwtToken = extractJwtTokenFromRequest(request);
        if(jwtToken != null){
            tokenBlacklistService.addTokenToBlacklist(jwtToken);
        }
    }

    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        }catch (DisabledException ex){
            throw new Exception("Profile disabled");
        }catch (BadCredentialsException ex){
            throw new Exception("Bad Credentials");
        }
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
