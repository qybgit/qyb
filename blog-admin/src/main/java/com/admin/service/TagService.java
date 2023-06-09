package com.admin.service;



import com.framework.dao.pojo.Tag;
import com.framework.vo.Result;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface TagService {
    Result selectAll();


    Result addTag(String nickname);

    List<Tag> selectByArticleId(Long id);

    Result selectCount();

    Result delTag(Integer id);

    Result revise(Tag tag);

    Result selectAlls();
}
