package com.beerhouse.repository;

import com.beerhouse.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Janaina Militão
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i WHERE i.id IN (:ingredintesId)")
    List<Ingredient> findAllByListId(@Param("ingredintesId") List<Long> ingredintesId);

    Optional<Ingredient> findById(Long id);

    Optional<Ingredient> findByName(String name);

}