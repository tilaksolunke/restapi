package in.tilaksalunke.restapi.repository;

import in.tilaksalunke.restapi.entity.ProfileEntity;
import in.tilaksalunke.restapi.io.ProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
