package com.framework.service;
import com.framework.dao.pojo.Category;
import com.framework.vo.Result;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category findById(int category_id);


    Result findAll();
}
