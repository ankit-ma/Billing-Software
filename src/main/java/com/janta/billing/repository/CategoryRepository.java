package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
