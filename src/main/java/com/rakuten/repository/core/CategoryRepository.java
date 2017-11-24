package com.rakuten.repository.core;

import com.rakuten.model.core.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select count(*) from category where LOWER(category.name) like :#{#categoryName} and category_id <> :#{#categoryId}",nativeQuery = true)
    long sameNameExists(@Param("categoryName") String categoryName, @Param("categoryId") int categoryId);
}
