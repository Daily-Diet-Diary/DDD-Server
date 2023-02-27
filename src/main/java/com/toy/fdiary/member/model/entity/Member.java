package com.toy.fdiary.member.model.entity;

import com.toy.fdiary.member.model.type.MemberStatus;
import com.toy.fdiary.security.oauth2.type.ProviderType;
import io.jsonwebtoken.Claims;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String name;

    private String nickname;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType; // 소셜 타입

    private String providerId;



    public Member(Claims claims) {
        this.memberId = Long.valueOf(claims.get("userId").toString());
        this.name = claims.get("name").toString();
    }
}
