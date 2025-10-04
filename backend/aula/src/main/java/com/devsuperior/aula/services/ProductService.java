package com.devsuperior.aula.services;

import com.devsuperior.aula.entities.Product;
import com.devsuperior.aula.entities.ProductDTO;
import com.devsuperior.aula.repositories.ProductRepository;
import com.devsuperior.aula.services.exceptions.DatabaseException;
import com.devsuperior.aula.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
      Page<Product> pages = ProductRepository.findAll(pageRequest);
      return pages.map(Product -> new ProductDTO(Product));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
      Optional <Product> obj = ProductRepository.findById(id);
      Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
      return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO save(ProductDTO ProductDTO) {
        Product entity = new Product();
        //entity.setName(ProductDTO.getName());
        entity = ProductRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id,ProductDTO dto) {
        try{
            Product entity = ProductRepository.getReferenceById(id);
            //entity.setName(dto.getName());
            entity = ProductRepository.save(entity);
            return new ProductDTO(entity);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Entity not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!ProductRepository.existsById(id)) {
            throw new EntityNotFoundException("Recurso não encontrado");
        }
        try {
            ProductRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }



}
