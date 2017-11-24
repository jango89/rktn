package com.rakuten.service.core;

import com.rakuten.exception.DuplicateNameException;
import com.rakuten.model.core.Category;
import com.rakuten.repository.core.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    public Category getCategory(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.delete(categoryId);
    }

    public Category saveCategory(final Category category) {

        final long sameNameExists = categoryRepository.sameNameExists(category.getName().toLowerCase().trim(), category.getCategoryId() == null ? 0 : category.getCategoryId());

        if (sameNameExists > 0) {
            throw new DuplicateNameException("Category name already exists");
        }

        if (category.getCategoryId() == null) {
            return insertCategory(category);

        } else {
            return updateCategory(category);
        }
    }

    private Category updateCategory(Category category) {
        final Category categoryFound = categoryRepository.findOne(category.getCategoryId());
        Assert.notNull(categoryFound, "Category not found");
        BeanUtils.copyProperties(category, categoryFound, getNonUpdatableProperties());
        return categoryRepository.save(categoryFound);
    }

    private Category insertCategory(Category category) {
        return categoryRepository.save(category);
    }

    private String[] getNonUpdatableProperties() {
        return new String[]{"name"};
    }
}
