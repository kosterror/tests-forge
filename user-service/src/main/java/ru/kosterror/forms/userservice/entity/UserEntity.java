package ru.kosterror.forms.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;

    private String surname;

    private String patronymic;

    @OneToMany(mappedBy = "owner")
    private List<RefreshTokenEntity> refreshTokens;

}
