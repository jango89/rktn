package com.rakuten.repository.core;

import com.rakuten.model.core.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(int categoryId);

    @Query(value = "select count(*) from product where LOWER(name) like :#{#name} and product_id <> :#{#productId} and category_id =  :#{#categoryId} ",nativeQuery = true)
    long sameNameExists(String name, int productId, int categoryId);
}
