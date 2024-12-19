package in.tilaksalunke.restapi.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    private String profileId;
    private String email;
    private String name;
    private Timestamp createdAT;
    private Timestamp updatedAt;
}
