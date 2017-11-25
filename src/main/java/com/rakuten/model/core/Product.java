package com.rakuten.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide valid name for product")
    @Length(max = 500, message = "Name should be between 1 and 500 characters", min = 1)
    private String name;

    @Column(name = "category_id")
    @NotNull(message = "*Please provide valid category")
    private int categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    @JsonIgnore
    private Category category;

    @Column(name = "description")
    @NotEmpty(message = "*Please provide valid description")
    @Length(max = 1000, message = "Description should be between 10 and 1000 characters", min = 10)
    private String description;

    @Column(name = "currency_id")
    @NotNull(message = "*Please provide valid currency")
    private Integer currencyId = 1;

    @OneToOne
    @JoinColumn(name = "currency_id", insertable = false, updatable = false)
    private Currency currency;

    @Column(name = "price")
    private BigDecimal price;

    public Product(){}

    public Product(String name, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return categoryId == product.categoryId &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, categoryId);
    }
}
