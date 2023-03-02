package com.toy.fdiary.member.model.entity;

import com.toy.fdiary.diary.model.entity.Diary;
import com.toy.fdiary.member.model.type.MemberStatus;
import com.toy.fdiary.security.oauth2.type.ProviderType;
import io.jsonwebtoken.Claims;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();



    public Member(Claims claims) {
        this.memberId = Long.valueOf(claims.get("userId").toString());
        this.name = claims.get("name").toString();
    }
}
