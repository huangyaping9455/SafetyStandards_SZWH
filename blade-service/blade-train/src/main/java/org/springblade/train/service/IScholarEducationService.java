/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.CertificateModel;
import org.springblade.train.entity.ScholarEducationModel;
import org.springblade.train.entity.TrainingListModel;
import org.springblade.train.page.ScholarEducationPage;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学员学历分析service
 */
public interface IScholarEducationService extends IService<ScholarEducationModel> {

    /**
     * 学者学历分析列表查询
     * @param scholarEducationPage
     * @return
     */
    ScholarEducationPage getScholarEducationList(ScholarEducationPage scholarEducationPage);

    /**
     * 查询课程结业证明
     * @param studentId
     * @param courseIdList
     * @return
     */
    CertificateModel getCertificateOfCompletiont(Integer studentId, List<Integer> courseIdList);
}
