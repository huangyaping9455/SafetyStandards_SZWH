package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 培训列表
 */
@Data
public class TrainingListModel implements Serializable {

    private Integer rowIndex;

    private String realName;

    private String identifyNumber;

    private String station;

    private String studyCount;

    private Integer score;

    private Integer studentId;

    private String result;
}
