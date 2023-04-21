/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.jinritixing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.jinritixing.entity.Baojingtongji;

/**
 * 视图实体类
 * @author 呵呵哒
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BaojingtongjiVO对象", description = "BaojingtongjiVO对象")
public class BaojingtongjiVO extends Baojingtongji {
	private static final long serialVersionUID = 1L;

	/**
	 * 年
	 */
	@ApiModelProperty(value = "年")
	private Integer nian;
	/**
	 * 月
	 */
	@ApiModelProperty(value = "月")
	private Integer yue;
	/**
	 * 日
	 */
	@ApiModelProperty(value = "日")
	private Integer ri;
}
