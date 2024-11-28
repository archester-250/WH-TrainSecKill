package com.pku.controller.category;

import com.pku.pojo.entity.Category;
import com.pku.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
}
