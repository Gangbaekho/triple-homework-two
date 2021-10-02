package com.nuitblanche.triple.domain.citylookuphistory;

import com.nuitblanche.triple.domain.BaseTimeEntity;
import com.nuitblanche.triple.domain.city.City;
import com.nuitblanche.triple.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CityLookUpHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    public void updateUser(User user){
        this.user = user;
    }

    public void updateCity(City city){
        this.city = city;
    }

}
