/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.jinritixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jinritixing.entity.Yujingquanxian;
import org.springblade.anbiao.jinritixing.page.YujingquanxianPage;
import org.springblade.anbiao.jinritixing.vo.YujingquanxianVO;

import java.util.List;

/**
 *  服务类
 */
public interface IYujingquanxianService extends IService<Yujingquanxian> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param yujingquanxian
	 * @return
	 */
	IPage<YujingquanxianVO> selectYujingquanxianPage(IPage<YujingquanxianVO> page, YujingquanxianVO yujingquanxian);

	/**
	 * 预警项分页
	 * @param yujingquanxianPage
	 * @return
	 */
	YujingquanxianPage<YujingquanxianVO> selectAllYuJing(YujingquanxianPage yujingquanxianPage);

	List<YujingquanxianVO> selectYuJingList(YujingquanxianPage page);

	boolean delYuJing(YujingquanxianPage page);

	boolean delYuJingByXiangId(YujingquanxianPage page);

	void yujingjiesuan(YujingquanxianPage page);

	/**
	 * 查询已创建预警项绑定的企业及相关预警项
	 * @param page
	 * @return
	 */
	YujingquanxianPage<YujingquanxianVO> selectYJDept(YujingquanxianPage page);

}
