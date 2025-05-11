package generator_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xk.kangdada.model.entity.Question;
import generator_1.service.QuestionService;
import com.xk.kangdada.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 小康
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2025-04-06 19:00:49
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




