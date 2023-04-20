package org.springblade.alarm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.alarm.entity.Alarmvehdailyreport;
import org.springblade.alarm.page.AlarmvehPage;

import java.util.List;

public interface AlarmvehdailyreportMapper extends BaseMapper<Alarmvehdailyreport> {

	 Integer  findpageList(AlarmvehPage alarmPage);


	List<Alarmvehdailyreport> chaosu(AlarmvehPage alarmvehPage);
	List<Alarmvehdailyreport> pilao(AlarmvehPage alarmvehPage);
	List<Alarmvehdailyreport> zhudonganquan(AlarmvehPage alarmvehPage);

}
