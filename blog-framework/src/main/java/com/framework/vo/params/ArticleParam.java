package com.framework.vo.params;



import com.framework.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private int category_id;

    private String summary;

    private List<Tag> tags;

    private String title;
}
