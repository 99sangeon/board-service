package sangeon.board.OAuth;

import lombok.Builder;
import lombok.Getter;
import sangeon.board.entity.member.Gender;
import sangeon.board.entity.member.Member;
import sangeon.board.entity.member.Role;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private LocalDate birth;
    private String email;
    private String mobile;
    private Gender gender;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String mobile, Gender gender, LocalDate birth) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.birth = birth;
    }

    public static OAuthAttributes of(String socialName, String userNameAttributeName, Map<String, Object> attributes){
        if("kakao".equals(socialName)){
            return ofKakao("id", attributes);
        }else if("naver".equals(socialName)) {
            return ofNaver("id", attributes);
        }

        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        Gender gender;
        gender = (response.get("gender").equals("M")) ? Gender.MALE : Gender.FEMALE;

        int birthyear  = Integer.valueOf((String) response.get("birthyear"));
        int[] birthday = new int[2];
        String[] monthAndDay = ((String) response.get("birthday")).split("-");
        birthday[0] = Integer.valueOf(monthAndDay[0]);
        birthday[1] = Integer.valueOf(monthAndDay[1]);

        LocalDate birth = LocalDate.of(birthyear, birthday[0], birthday[1]);

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .mobile((String) response.get("mobile"))
                .gender(gender)
                .birth(birth)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .phoneNumber(mobile)
                .birth(birth)
                .gender(gender)
                .build();
    }
}
