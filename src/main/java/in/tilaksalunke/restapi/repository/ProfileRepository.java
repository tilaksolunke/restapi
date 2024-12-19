package in.tilaksalunke.restapi.repository;

import in.tilaksalunke.restapi.entity.ProfileEntity;
import in.tilaksalunke.restapi.io.ProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
}
