package com.yourproject.controller;

import com.yourproject.service.CategoryCourseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final CategoryCourseCache categoryCourseCache;

    @Autowired
    public CacheController(CategoryCourseCache categoryCourseCache) {
        this.categoryCourseCache = categoryCourseCache;
    }

    @GetMapping("/stats")
    public Map<String, Object> getCacheStats() {
        return Map.of(
                "cacheSize", categoryCourseCache.size(),
                "cachedCategories", categoryCourseCache.getCachedCategoryIds()
        );
    }

    @PostMapping("/clear")
    public void clearCache(@RequestParam(required = false) Long categoryId) {
        if (categoryId == null) {
            categoryCourseCache.clear();
        } else {
            categoryCourseCache.invalidate(categoryId);
        }
    }
}