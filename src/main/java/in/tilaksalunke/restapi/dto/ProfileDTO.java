package in.tilaksalunke.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {
    private String profileId;
    private String email;
    private String name;
    private String password;
    private Timestamp createdAT;
    private Timestamp updatedAt;
}
