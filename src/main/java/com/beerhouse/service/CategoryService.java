package com.beerhouse.service;

import com.beerhouse.exception.NotFoundException;
import com.beerhouse.model.Category;
import com.beerhouse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Janaina Militão
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private static String MSG_CATEGORY_NOT_FOUND = "Category not found";

    public void create(String name) throws Exception {
        Optional<Category> categoryCreated = categoryRepository.findByName(name);
        if(categoryCreated.isPresent()){
            throw new Exception("Category already registered");
        }
        Category category = new Category(name);
        categoryRepository.saveAndFlush(category);
        log.info("Create category: "+category.toString());
    }

    public List<Category> list(){
        log.info("List category");
        return categoryRepository.findAll();
    }

    public void delete(Long categoryId) throws NotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(MSG_CATEGORY_NOT_FOUND));
        log.info("Delete beer: "+category.toString());
        categoryRepository.delete(category);
    }
}