package com.beerhouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient  implements Serializable {

    @Id
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "ingredient_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ingredient_seq")
    private Long id;

    @NotNull(message = "name: required field")
    private String name;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="Ingredient_Beer",
//            joinColumns={@JoinColumn(name = "ingredient_id")},
//            inverseJoinColumns={@JoinColumn(name = "beer_id")})
//    private List<Beer> beers;

    public Ingredient(String name){
        this.name = name;
    }

}
