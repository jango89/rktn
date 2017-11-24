package com.rakuten.model.core;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name", length = 500)
    @NotEmpty(message = "*Please provide valid name for category")
    @Length(max = 500, message = "Name should be between 1 and 500 characters", min = 1)
    private String name;

    @Column(name = "description", length = 1000)
    @NotEmpty(message = "*Please provide valid description")
    @Length(max = 1000, message = "Description should be between 10 and 1000 characters", min = 10)
    private String description;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
