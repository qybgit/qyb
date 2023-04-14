package com.admin.vo.params;

import com.framework.dao.pojo.Category;
import com.framework.dao.pojo.Tag;
import lombok.Data;

@Data
public class SearchArticle {
    private Tag tag;
    private Category category;

}
