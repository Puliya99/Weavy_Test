package lk.ijse.gdse.weavy_test.dto;

import lk.ijse.gdse.weavy_test.enums.Metadata;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto implements SuperDto {

    private String uid;
    private String email;
    private String givenName;
    private String middleName;
    private String name;
    private String familyName;
    private String nickname;
    private String phoneNumber;
    private String comment;
    private String picture;
    private String directory;
    private Metadata metadata;
    private List<String> tags;
    private boolean isSuspended;
    private boolean isBot;

}
