package generator_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xk.kangdada.model.entity.App;
import generator_1.service.AppService;
import com.xk.kangdada.mapper.AppMapper;
import org.springframework.stereotype.Service;

/**
* @author 小康
* @description 针对表【app(应用)】的数据库操作Service实现
* @createDate 2025-04-06 19:00:49
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService{

}




