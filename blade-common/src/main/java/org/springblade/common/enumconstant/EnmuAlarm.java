package org.springblade.common.enumconstant;

/**
 * @author 呵呵哒
 * @projectName SafetyStandards
 */
public interface EnmuAlarm {

    /**
     * @author 呵呵哒
     * @description: 报警结果_处理状态字典
     * @projectName SafetyStandards
     */
    enum ChuliJieguo{
        /**
         * 未处理
         */
        WEICHULI("WEICHULI", 0, "未处理"),
        /**
         * 已处理
         */
        YICHULI("YICHULI", 1, "已处理"),
        /**
         *超期处理
         */
        CHAOSHI_CHULI("CHAOSHI_CHULI", 2, "超期处理"),
        /**
         *已处理未通过
         */
        YICHULI_WEITONGGUO("YICHULI_WEITONGGUO", 3, "已处理未通过");

        private ChuliJieguo(String id, Integer value ,String desc) {
            this.id = id;
            this.value = value;
            this.desc = desc;
        }
        /**
         * 常量id
         */
        public final String id;
        /**
         * 处理状态类型
         */
        public final Integer value;
        /**
         * 字典名称
         */
        public final String desc;

        public final static String enumId = "ChuliJieguo";
        public final static String enumDesc = "报警结果_处理状态字典";

        public static ChuliJieguo getByValue(Integer value){
            ChuliJieguo[] enums = ChuliJieguo.values();
            for (ChuliJieguo anEnum : enums) {
                if(anEnum.value.equals(value)){
                    return anEnum;
                }
            }
            return null;
        }

    }



    /**
     * @description: 报警结果_申诉状态字典
     * @projectName SafetyStandards
     * @date 2019/9/249:31
     */
    enum ShensuJieguo{
        /**
         * 未申诉
         */
        WEISHENSU("WEISHENSU", 0, "未申诉"),
        /**
         * 已申诉
         */
        YISHENSU("YISHENSU", 1, "已申诉"),
        /**
         *超时申诉
         */
        CHAOSHI_SHENSU("CHAOSHI_SHENSU", 2, "超时申诉"),
        /**
         * 已申诉未通过
         */
        YISHENSU_WEITONGGUO("YISHENSU_WEITONGGUO", 3, "已申诉未通过"),
		/**
		 * 审核中
		 */
		SHENHEZHONG("SHENHEZHONG", 4, "审核中"),
		/**
		 *申诉通过
		 */
		SHENSUTONGGUO("SHENSUTONGGUO", 5, "申诉通过"),
		/**
		 * 申诉驳回
		 */
		SHENSUBOHUI("SHENSUBOHUI", 6, "申诉驳回");

		private ShensuJieguo(String id, Integer value ,String desc) {
            this.id = id;
            this.value = value;
            this.desc = desc;
        }
        /**
         * 常量id
         */
        public final String id;
        /**
         * 申诉状态类型
         */
        public final Integer value;
        /**
         * 字典名称
         */
        public final String desc;

        public final static String enumId = "ShensuJieguo";
        public final static String enumDesc = "报警结果_申诉状态字典";

        public static ShensuJieguo getByValue(Integer value){
			ShensuJieguo[] enums = ShensuJieguo.values();
            for (ShensuJieguo anEnum : enums) {
                if(anEnum.value.equals(value)){
                    return anEnum;
                }
            }
            return null;
        }

    }
}
