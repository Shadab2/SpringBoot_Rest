package com.shadab.expensetrackerapi.resources;

import com.shadab.expensetrackerapi.domain.Category;
import com.shadab.expensetrackerapi.domain.Transaction;
import com.shadab.expensetrackerapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest req){
        int userId = (Integer) req.getAttribute("userId");
        List<Category> categories = categoryService.fetchAllCategories(userId);
        return  new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest req,@PathVariable("categoryId") Integer categoryId ){
        Integer userId = (Integer)req.getAttribute("userId");
        Category category = categoryService.fetchCategoryById(userId,categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Category> addCategory(HttpServletRequest req , @RequestBody Map<String,Object> categoryMap){
        int userId = (Integer) req.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        Category category = categoryService.addCategory(userId, title, description);
        return new ResponseEntity<Category>(category, HttpStatus.CREATED);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest req,@PathVariable("categoryId") Integer categoryId,
                                                              @RequestBody Category category){
        Integer userId = (Integer)req.getAttribute("userId");
        categoryService.updateCategory(userId,categoryId,category);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest request,
                                                               @PathVariable("categoryId") Integer categoryId) {
        int userId = (Integer) request.getAttribute("userId");
        categoryService.removeCategoryWithAllTransaction(userId, categoryId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
