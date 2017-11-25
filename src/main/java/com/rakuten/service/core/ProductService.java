package com.rakuten.service.core;

import com.rakuten.exception.DuplicateNameException;
import com.rakuten.model.core.Product;
import com.rakuten.repository.core.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(new Sort(Sort.Direction.ASC, "categoryId"));
    }

    public void deleteProduct(int productId) {
        productRepository.delete(productId);
    }

    public Product getProduct(int productId) {
        return productRepository.findOne(productId);
    }

    public Product saveProduct(Product product) {

        final long sameNameExists = productRepository.sameNameExists(product.getName().toLowerCase().trim(),
                product.getProductId() == null ? 0 : product.getProductId(), product.getCategoryId());

        if (sameNameExists > 0) {
            throw new DuplicateNameException("Product name already exists");
        }

        product.setCurrency(null);
        if (product.getProductId() == null) {
            insertProduct(product);
        } else {
            updateProduct(product);
        }

        return productRepository.findOne(product.getProductId());
    }

    private Product updateProduct(Product product) {
        final Product productFound = productRepository.findOne(product.getProductId());
        Assert.notNull(productFound, "Product not found");
        BeanUtils.copyProperties(product, productFound, getNonUpdatableProperties());
        return productRepository.save(productFound);
    }

    private Product insertProduct(Product product) {
        return productRepository.save(product);
    }

    private String[] getNonUpdatableProperties() {
        return new String[]{"name", "categoryId"};
    }
}
