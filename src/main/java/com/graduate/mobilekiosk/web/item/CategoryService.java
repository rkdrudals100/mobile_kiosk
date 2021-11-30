package com.graduate.mobilekiosk.web.item;

import com.graduate.mobilekiosk.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transient
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 사용자 정보를 사용하여 검색
    public Category findByCategoryName(String categoryName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return categoryRepository.findByNameAndUserName(categoryName, authentication.getName());
    }
}
