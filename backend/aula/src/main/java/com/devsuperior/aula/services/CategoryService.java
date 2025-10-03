package com.devsuperior.aula.services;

import com.devsuperior.aula.entities.Category;
import com.devsuperior.aula.entities.CategoryDTO;
import com.devsuperior.aula.repositories.CategoryRepository;
import com.devsuperior.aula.services.exceptions.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
      List<Category> list = categoryRepository.findAll();
      return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
      Optional <Category> obj = categoryRepository.findById(id);
      Category category = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
      return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setName(categoryDTO.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }


}
