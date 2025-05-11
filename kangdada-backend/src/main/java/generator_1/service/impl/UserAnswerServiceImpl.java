package generator_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xk.kangdada.model.entity.UserAnswer;
import generator_1.service.UserAnswerService;
import com.xk.kangdada.mapper.UserAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author 小康
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service实现
* @createDate 2025-04-06 19:00:49
*/
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer>
    implements UserAnswerService{

}




