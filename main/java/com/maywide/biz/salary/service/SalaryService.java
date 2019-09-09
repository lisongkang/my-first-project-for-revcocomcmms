package com.maywide.biz.salary.service;

import com.maywide.biz.salary.entity.ExplicationConfig;
import com.maywide.core.service.PersistentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>用于本系统中录入导入数据
 *
 * </p>
 *
 * @author Agile Code Generator
 * @since 2019-06-15
 */
@Service
public class SalaryService {

    @Autowired
    private PersistentService persistentService;

    public List<ExplicationConfig> getExplicationConfigs() throws Exception {
        ExplicationConfig explicationConfig = new ExplicationConfig();
        return persistentService.find(explicationConfig);
    }

}
