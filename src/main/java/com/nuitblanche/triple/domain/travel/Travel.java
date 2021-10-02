package com.nuitblanche.triple.domain.travel;

import com.nuitblanche.triple.domain.BaseTimeEntity;
import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Travel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Travel(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateUser(User user){
        this.user = user;
    }

    public void updateCity(City city){
        this.city = city;
    }
}
