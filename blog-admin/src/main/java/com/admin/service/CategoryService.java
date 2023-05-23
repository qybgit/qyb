package com.admin.service;
import com.framework.dao.pojo.Category;
import com.framework.vo.Result;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category findById(int category_id);


    Result findAll();

    Result delCategory(Integer id);

    Result addCategory(Category category);

    Result revise(Category category);

    Result findAlls();
}
