package com.store.repositories;

import com.store.entities.Category;
import com.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,String> {
    //Search by title

    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
   //Page<Product> searchByTitle(String subtitle,int pageNumber,int pageSize, String sortBy, String sortDir);

    Page<Product> findByCategory(Category category,Pageable pageable);
}
