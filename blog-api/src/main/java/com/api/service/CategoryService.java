package com.api.service;

import com.api.dao.pojo.Category;
import com.api.vo.Result;

public interface CategoryService {
    Category findById(int category_id);


    Result findAll();
}
