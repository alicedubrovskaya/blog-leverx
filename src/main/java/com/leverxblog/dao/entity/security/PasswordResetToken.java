package com.leverxblog.dao.entity.security;

import com.leverxblog.dao.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private Date expiryDate;

    public PasswordResetToken(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @OneToOne
    @JoinColumn(nullable = false, name = "userEntity_id")
    private UserEntity userEntity;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
