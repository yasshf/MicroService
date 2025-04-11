package com.yourproject.service;

import com.yourproject.model.Course;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CategoryCourseCache {
    private final Map<Long, List<Course>> cache = new ConcurrentHashMap<>();
    private final int MAX_CACHE_SIZE = 100;

    public List<Course> getCourses(Long categoryId) {
        return cache.get(categoryId);
    }

    public void putCourses(Long categoryId, List<Course> courses) {
        if (cache.size() >= MAX_CACHE_SIZE) {
            Long lruKey = cache.keySet().iterator().next();
            cache.remove(lruKey);
        }
        cache.put(categoryId, courses);
    }

    public void invalidate(Long categoryId) {
        cache.remove(categoryId);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public Set<Long> getCachedCategoryIds() {
        return cache.keySet();
    }
}