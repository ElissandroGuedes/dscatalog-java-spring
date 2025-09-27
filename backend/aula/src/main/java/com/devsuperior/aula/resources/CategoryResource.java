package com.devsuperior.aula.resources;

import com.devsuperior.aula.entities.Category;
import com.devsuperior.aula.entities.CategoryDTO;
import com.devsuperior.aula.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> list = categoryRepository.findAll();
        List<CategoryDTO> dtos = list.stream().map(CategoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtos);
    }
}
