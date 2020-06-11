package com.leverxblog.entity;

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
public class VerificationToken {
    private static final int EXPIRATION=60 *24;  //?

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    public VerificationToken(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
    }

    @OneToOne //дописать
    @JoinColumn(nullable = false, name="userEntity_id")
    private UserEntity userEntity;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

}
