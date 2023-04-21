package org.springblade.doc.biaozhunhuamuban.entity;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import lombok.Getter;
import lombok.Setter;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.enumconstant.EnmuDoc;
import org.springblade.common.tool.CommonUtil;
import org.springblade.common.tool.CopyFile;
import org.springblade.common.tool.Excel2PdfUtil;
import org.springblade.common.tool.WrodUtil;
import org.springblade.doc.biaozhunhuamuban.vo.BiaozhunhuamubanVO;
import org.springblade.doc.qiyewenjian.entity.Qiyewenjian;
import org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 文件读取，解析路径与层级关系
 * @ClassName FileParse
 * @Author th
 * @CreateDate 2019-05-04 17:31
 **/
public class FileParse {

	/**
	 * 标准化文件各层级File相关信息
	 */
	@Getter
	private List<Biaozhunhuamuban> list = new CopyOnWriteArrayList<Biaozhunhuamuban>();

	/**
	 * ABCD文档各层级File相关信息
	 */
	@Getter
	List<SafetyProductionFile> abcdList = new CopyOnWriteArrayList<SafetyProductionFile>();


	/**
	 * 文件默认id（取表中最大id+1）
	 */
	@Setter
	private Integer id ;
	@Setter
	private Integer deptId;
	@Setter
	private  String deptName;

	@Autowired
	private FileServer fileServer;

	/**
	 *将模板中的doc文件转换成docx
	 * @author: hyp
	 * @CreateDate2021-05-15 0:51
	 * @param list
	 * @return: java.util.List<org.springblade.anbiao.Biaozhunhuamuban>
	 */
	public  List<Biaozhunhuamuban> convertDocx(List<Biaozhunhuamuban> list) throws Exception {
		for(Biaozhunhuamuban muban :list){
			String oldPath = this.getPath(muban.getPath());
			String extName = FileUtil.extName(oldPath);
			//扩展名为doc，就转换成docx

			if("doc".equals(extName)){
				//替换后缀.doc为.docx得到新路径
				String newMubanPath = StrUtil.replace(oldPath,".doc",".docx");
				//转换为docx
				CommonUtil.convertDocFmt(oldPath,newMubanPath,CommonUtil.DOCX_FMT);
				//删除doc文件
				FileUtil.del(oldPath);
				//设置新docx文件的属性入库
				muban.setPath(this.getInsertPath(newMubanPath));
				muban.setMubanPath(this.getInsertPath(newMubanPath));
				muban.setName(StrUtil.replace(muban.getName(),"doc","docx"));

				System.out.println("doc转docx完成："+muban.getName());

			}
		}
		return list;
	}

	/**
	 *获取存入数据的路径
	 * @author: hyp
	 * @date: 2019/5/20 16:56
	 * @param path 物理路径
	 * @return: java.lang.String
	 */
	public  String getInsertPath(String path) {
		return StrUtil.replace(path,fileServer.getPathPrefix(),"");
	}

	/**
	 *获取物理路径path
	 * @author: hyp
	 * @date: 2019/5/20 16:57
	 * @param insertPath 存入路径
	 * @return: java.lang.String
	 */
	public  String getPath(String insertPath) {
		return fileServer.getPathPrefix()+insertPath;
	}

	/**
	 *物理路径转url
	 * @author: hyp
	 * @date: 2019/5/20 16:58
	 * @param path
	 * @return: java.lang.String
	 */
	public  String pathToUrl(String path) {
		return StrUtil.replace(StrUtil.replace(path,fileServer.getPathPrefix(),fileServer.getUrlPrefix()),"\\","/");
	}

	/**
	 *入库路径转换为url
	 * @author: hyp
	 * @date: 2019/5/20 17:48
	 * @param insertPath
	 * @return: java.lang.Object
	 */
	public  String insertPathToUrl(String insertPath) {
		return StrUtil.replace(fileServer.getUrlPrefix()+insertPath,"\\","/");
	}


	/**
	  *
	  * @Author th
	  * @CreateDate 2019-05-04 19:51
	  * @Param [file文件, upperid上级id, tier层级]
	  * @return void
	  **/
	public  void parseFile(File file, int upperid, String tier) {
		System.out.println("递归获取："+file.getName());
		//当前层级排序号
		int sort = 1;
		//读取目录后进行排序
		File[] files= this.orderByName(file);
		for(File f:files) {         //遍历目录


			if(f.isDirectory()){            //是否为目录
				Biaozhunhuamuban muban = new Biaozhunhuamuban();
				muban.setId(this.id);
				muban.setDeptId(this.deptId);
				muban.setParentId(upperid);
				muban.setName(f.getName());
				//去掉物理路径root
				muban.setPath(this.getInsertPath(f.getPath()));
				muban.setMubanPath(this.getInsertPath(f.getPath()));
				muban.setType("文件夹");
				muban.setSort(sort);
				String atier = tier+"-"+this.id;
				muban.setTier(atier);

				this.list.add(muban);
				sort++;
				int aid =id;
				this.id++;

				parseFile(f,aid,atier);        //递归调用
			}else{
				Biaozhunhuamuban muban = new Biaozhunhuamuban();
				muban.setId(this.id);
				muban.setDeptId(this.deptId);
				muban.setParentId(upperid);
				muban.setName(f.getName());
				//去掉物理路径root
				muban.setPath(this.getInsertPath(f.getPath()));
				muban.setMubanPath(this.getInsertPath(f.getPath()));
				muban.setType("文件");
				muban.setSort(sort);
				muban.setTier(tier+"-"+this.id);
				muban.setSort(sort);
				this.list.add(muban);

				sort++;
				this.id++;
			}

		}
	}



	/**
	  * 目录文件排序（文件夹>文件>记录）如果文件类型相同，则按照文件前缀数字排序
	  * @Author th
	  * @CreateDate 2019-05-04 19:55
	  * @Param [file]
	  * @return java.io.File[]
	  **/
	public  File[] orderByName(File file) {
		File[] files = file.listFiles();
		List<File> fileList = Arrays.asList(files);
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile()){
					return -1;
				}else if (o1.isFile() && o2.isDirectory()){
					return 1;
				}else if(!o1.getName().contains("记录") && o2.getName().contains("记录")){//
					return -1;
				}else if(o1.getName().contains("记录") && !o2.getName().contains("记录")){
					return 1;
				}else if((o1.getName().contains("记录") && o2.getName().contains("记录")) ||
					(!o1.getName().contains("记录") && !o2.getName().contains("记录"))){
					List<Integer> o1List = FileParse.getNum(o1.getName());
					List<Integer> o2List = FileParse.getNum(o2.getName());
					return o1List.get(0) - o2List.get(0);
				}

				return o1.getName().compareTo(o2.getName());
			}
		});
		return files;
	}

	/**
	  * 获取文件名称包含的数字
	  * @Author th
	  * @CreateDate 2019-05-04 19:58
	  * @Param [str]
	  * @return java.util.List<java.lang.Integer>
	  **/
	private static   List<Integer> getNum(String str){
		str.trim();   //直接获取传递过来的字符串中的数字（适用于字符串中仅仅包含一个数字）
		List<Integer>  nums=new ArrayList<Integer>();
		for(String sss:str.replaceAll("[^0-9]", ",").split(",")){//首先把字符串中非数字的替换成“，”，然后用“，”把字符串分割成一个数组
			if (sss.length()>0){
				nums.add(Integer.valueOf(sss));
			}
		}
		nums.add(0);//追加0，避免集合长度为0，排序失败（不含数字的，默认排前面）
		return nums;
	}

	/**
	 *批量生成正式文件，pdf，图片
	 * @author: hyp
	 * @CreateDate2021-05-15 20:07
	 * @param list
	 * @param deptName
	 * @return: java.util.List<org.springblade.anbiao.Biaozhunhuawenjian>
	 */
	public  void creatFormalFile(List<Biaozhunhuamuban> list,String deptName) throws IOException {
		List<Biaozhunhuawenjian> wenjianList = new ArrayList<Biaozhunhuawenjian>();
		//遍历模板文件list
		for(Biaozhunhuamuban muban :list){
			//如果不是文件，直接下次循环
			if(!"文件".equals(muban.getType())){
				continue;
			}

			//当前后缀名
			String expandedName = muban.getName().substring(muban.getName().lastIndexOf('.'));

			//如果类型是docx文件,就可以存取到正式文件
			if(".docx".equals(expandedName)){
				System.out.println("正在生成："+muban.getName());
				//生成文件父级目录
				FileUtil.mkParentDirs(this.getPath(muban.getPath()));
				//替换路径前缀,获得正式文件路径
				String wenjianPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_FORMAL_PATH);

				//复制到正式文件中,以后将改成模板替换
				//FileUtil.copy(muban.getPath(),wenjianPath,true);

				//to do 封装
				XWPFTemplate template = XWPFTemplate.compile(this.getPath(muban.getPath())).render(new HashMap<String, Object>(){{
					put("dpetName", deptName);
				}});
				if(!FileUtil.exist(wenjianPath)){
					FileUtil.mkParentDirs(wenjianPath);
				}
				FileOutputStream out = new FileOutputStream(wenjianPath);
				template.write(out);
				out.flush();
				out.close();
				template.close();

				//替换路径前缀,获得pdf文件路径
				String pdfPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_PDF_PATH);
				pdfPath = StrUtil.replace(pdfPath,expandedName,".pdf");
				//生成文件父级目录
				FileUtil.mkParentDirs(pdfPath);
				//生成pdf到pdf文件路径
				CommonUtil.world2pdf(wenjianPath,pdfPath);
				System.out.println("已生成pdf"+pdfPath);

				//替换路径前缀,获得图片文件路径
				String imgDirPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_IMG_PATH);
				//图片可能是多个,存放在目录中,这里去掉扩展名
				imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
				//生成图片文件目录
				FileUtil.mkdir(imgDirPath);
				//生成pdf到pdf文件路径
				CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
				System.out.println("已生成图片"+imgDirPath);
				System.out.println("生成成功");

			}
		}
	}

	/**
	 *批量生成正式文件，pdf，图片
	 * @author: hyp
	 * @CreateDate2021-05-15 20:07
	 * @param muban
	 * @param map
	 */
	public  void creatFormalFile(Biaozhunhuamuban muban,Map<String,Object> map) throws IOException {

		//当前后缀名
		String expandedName = muban.getName().substring(muban.getName().lastIndexOf('.'));
		//如果类型是docx文件,就可以存取到正式文件
		if(".docx".equals(expandedName)){
			System.out.println("正在生成："+muban.getName());
			//生成文件父级目录
			FileUtil.mkParentDirs(this.getPath(muban.getPath()));
			//替换路径前缀,获得正式文件路径
			String wenjianPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_FORMAL_PATH);

			//复制到正式文件中,以后将改成模板替换
			//FileUtil.copy(muban.getPath(),wenjianPath,true);

			System.out.println(muban.getPath());
			System.out.println("!!!!!!!!!!!!!!!!!!!!");
			System.out.println(map);
			System.out.println("11111111111111111111");
			System.out.println(this.getPath(muban.getPath()));
			System.out.println(map);
			//to do 封装
			String paths = this.getPath(muban.getPath());
			XWPFTemplate template = XWPFTemplate.compile(paths).render(map);
			if(!FileUtil.exist(wenjianPath)){
				FileUtil.mkParentDirs(wenjianPath);
			}
			FileOutputStream out = new FileOutputStream(wenjianPath);
			template.write(out);
			out.flush();
			out.close();
			template.close();

			//替换路径前缀,获得pdf文件路径
			String pdfPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_PDF_PATH);
			pdfPath = StrUtil.replace(pdfPath,expandedName,".pdf");
			//生成文件父级目录
			FileUtil.mkParentDirs(pdfPath);
			//生成pdf到pdf文件路径
			System.out.println("正在生成："+pdfPath);
			System.out.println("111111111111111111111");
			System.out.println(wenjianPath);
			CommonUtil.world2pdf(wenjianPath,pdfPath);
			System.out.println("已生成pdf"+pdfPath);

			//替换路径前缀,获得图片文件路径
			String imgDirPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_IMG_PATH);
			//图片可能是多个,存放在目录中,这里去掉扩展名
			imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
			//生成图片文件目录
			FileUtil.mkdir(imgDirPath);
			//生成pdf到图片文件路径
			System.out.println("正在生成图片"+imgDirPath);
			CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
			System.out.println("已生成图片"+imgDirPath);
			System.out.println("生成成功");

		}else{
			String paths = this.getPath(muban.getPath());
			//替换路径前缀,获得正式文件路径
			String pdfPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_PDF_PATH);
			if(!FileUtil.exist(pdfPath)){
				FileUtil.mkParentDirs(pdfPath);
			}
			//需要复制的文件
			File f = new File(paths);
			//复制的文件
			File f2 = new File(pdfPath);
			//复制的文件
			CopyFile.method1(f,f2);
			//替换路径前缀,获得图片文件路径
			String imgDirPath = StrUtil.replace(this.getPath(muban.getPath()), FilePathConstant.SUBTEMPLATE_PATH, FilePathConstant.SUBTEMPLATE_IMG_PATH);
			//图片可能是多个,存放在目录中,这里去掉扩展名
			imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
			//生成图片文件目录
			FileUtil.mkdir(imgDirPath);
			//生成pdf到图片文件路径
			System.out.println("正在生成图片"+imgDirPath);
			CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
			System.out.println("已生成图片"+imgDirPath);
			System.out.println("生成成功");
		}
	}

	/**
	 *企业文件生成对应文件
	 * @author: hyp
	 * @date: 2019/7/5 13:04
	 * @param parent
	 * @return: void
	 */
	public void creatFormalFile(Qiyewenjian parent) {
		//当前后缀名
		String expandedName = parent.getName().substring(parent.getName().lastIndexOf('.'));

		//如果类型是docx文件,就可以存取到正式文件
		if(".docx".equals(expandedName)){
			//替换路径前缀,获得pdf文件路径
			String pdfPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.QYWJ_PATH, FilePathConstant.QYWJ_PDF_PATH);
			pdfPath = StrUtil.replace(pdfPath,expandedName,".pdf");
			//生成文件父级目录
			FileUtil.mkParentDirs(pdfPath);
			//生成pdf到pdf文件路径
			CommonUtil.world2pdf(this.getPath(parent.getPath()),pdfPath);
			System.out.println("已生成pdf"+pdfPath);

			//替换路径前缀,获得图片文件路径
			String imgDirPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.QYWJ_PATH, FilePathConstant.QYWJ_IMG_PATH);
			//图片可能是多个,存放在目录中,这里去掉扩展名
			imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
			//生成图片文件目录
			FileUtil.mkdir(imgDirPath);
			//生成pdf到pdf文件路径
			CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
			System.out.println("已生成图片"+imgDirPath);

			System.out.println("生成成功");

		}
	}




	/**
	 *生产对应ABCD文档
	 * @author: hyp
	 * @date: 2019/8/2 12:12
	 * @param parent
	 * @return: void
	 */
	public void creatFormalFile(SafetyProductionFile parent) throws Exception {
		//当前后缀名
		String expandedName = parent.getName().substring(parent.getName().lastIndexOf('.'));

		//验证文件是不是world文件格式
		if(".docx".equals(expandedName) || ".doc".equals(expandedName)){
			//替换路径前缀,获得pdf文件路径
			String pdfPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_PDF_PATH);
			pdfPath = StrUtil.replace(pdfPath,expandedName,".pdf");
			//生成文件父级目录
			FileUtil.mkParentDirs(pdfPath);
			//生成pdf到pdf文件路径
			CommonUtil.world2pdf(this.getPath(parent.getPath()),pdfPath);
			System.out.println("已生成pdf"+pdfPath);

			//替换路径前缀,获得图片文件路径
			String imgDirPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_IMG_PATH);
			//图片可能是多个,存放在目录中,这里去掉扩展名
			imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
			//生成图片文件目录
			FileUtil.mkdir(imgDirPath);
			//生成pdf到pdf文件路径
			CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
			System.out.println("已生成图片"+imgDirPath);

			System.out.println("生成成功");
		}
		//验证文件是不是Excel文件格式
		else if(".xlsx".equals(expandedName) || ".xls".equals(expandedName)){
			//替换路径前缀,获得pdf文件路径
			String pdfPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_PDF_PATH);
			pdfPath = StrUtil.replace(pdfPath,expandedName,".pdf");
			//生成文件父级目录
			FileUtil.mkParentDirs(pdfPath);
			//生成pdf到pdf文件路径
			//将Excel转换成PDF，传入文件地址、生成文件的地址
			Excel2PdfUtil.excel2pdf(this.getPath(parent.getPath()), pdfPath);
			System.out.println("已生成pdf"+pdfPath);

			//替换路径前缀,获得图片文件路径
			String imgDirPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_IMG_PATH);
			//图片可能是多个,存放在目录中,这里去掉扩展名
			imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
			//生成图片文件目录
			FileUtil.mkdir(imgDirPath);
			//生成pdf到pdf文件路径
			CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
			System.out.println("已生成图片"+imgDirPath);

			System.out.println("生成成功");
		}else{
			String paths = this.getPath(parent.getPath());
			//替换路径前缀,获得正式文件路径
			String pdfPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_PDF_PATH);
			if(!FileUtil.exist(pdfPath)){
				FileUtil.mkParentDirs(pdfPath);
			}
			//需要复制的文件
			File f = new File(paths);
			//复制的文件
			File f2 = new File(pdfPath);
			//复制的文件
			CopyFile.method1(f,f2);
			//替换路径前缀,获得图片文件路径
			String imgDirPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_IMG_PATH);
			//图片可能是多个,存放在目录中,这里去掉扩展名
			imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
			//生成图片文件目录
			FileUtil.mkdir(imgDirPath);
			//生成pdf到图片文件路径
			System.out.println("正在生成图片"+imgDirPath);
			CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
			System.out.println("已生成图片"+imgDirPath);
			System.out.println("生成成功");
		}
	}



	/**
	 *生产对应ABCD图片文档
	 * @author: hyp
	 * @date: 2019/8/2 12:12
	 * @param map
	 * @param parent
	 * @return: void
	 */
	public void creatFormalImageFile(Map<String, PictureRenderData> map, SafetyProductionFile parent) throws IOException {
		List<String> kList = new ArrayList<String>();
		map.keySet().forEach(item->kList.add(item));
		//当前后缀名
		String expandedName = parent.getName().substring(parent.getName().lastIndexOf('.'));
		InputStream inputStream = WrodUtil.creatImageTemplate(kList);


		XWPFTemplate template = XWPFTemplate.compile(inputStream).render(map);
		String wenjianPath = this.getPath(parent.getPath());
		if(!FileUtil.exist(wenjianPath)){
			FileUtil.mkParentDirs(wenjianPath);
		}
		FileOutputStream out = new FileOutputStream(wenjianPath);
		template.write(out);
		out.flush();
		out.close();
		template.close();
		inputStream.close();
		//替换路径前缀,获得pdf文件路径
		String pdfPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_PDF_PATH);
		pdfPath = StrUtil.replace(pdfPath,expandedName,".pdf");
		//生成文件父级目录
		FileUtil.mkParentDirs(pdfPath);
		//生成pdf到pdf文件路径
		CommonUtil.world2pdf(this.getPath(parent.getPath()),pdfPath);
		System.out.println("已生成pdf"+pdfPath);

		//替换路径前缀,获得图片文件路径
		String imgDirPath = StrUtil.replace(this.getPath(parent.getPath()), FilePathConstant.SPF_PATH, FilePathConstant.SPF_IMG_PATH);
		//图片可能是多个,存放在目录中,这里去掉扩展名
		imgDirPath = StrUtil.replace(imgDirPath,expandedName,"");
		//生成图片文件目录
		FileUtil.mkdir(imgDirPath);
		//生成pdf到pdf文件路径
		CommonUtil.pdf2Image(pdfPath,imgDirPath,300,0);
		System.out.println("已生成图片"+imgDirPath);

		System.out.println("生成成功");





	}

	/**
	 *标准化文件模板数据解析成企业数据
	 * @author: hyp
	 * @date: 2019/9/3 10:55
	 * @param mubanList
	 * @return: java.util.List<org.springblade.doc.biaozhunhuamuban.vo.BiaozhunhuamubanVO>
	 */
    public void parseMubanList(List<BiaozhunhuamubanVO> mubanList, int upperid, String  tier) {
		String p = EnmuDoc.MubanReplacePath.getByValue(2).path;
		for (BiaozhunhuamubanVO biaozhunhuamubanVO : mubanList) {
			biaozhunhuamubanVO.setParentId(upperid);
			biaozhunhuamubanVO.setId(this.id);
			biaozhunhuamubanVO.setDeptId(this.deptId);
			biaozhunhuamubanVO.setIs_muban(1);
			biaozhunhuamubanVO.setPath(StrUtil.replace(biaozhunhuamubanVO.getPath(),p,this.deptName));
			String atier = tier+"-"+this.id;
			biaozhunhuamubanVO.setTier(atier);
			this.list.add(biaozhunhuamubanVO);
			int aid = id;
			this.id++;
			List<BiaozhunhuamubanVO> childrenList = (List<BiaozhunhuamubanVO>)(Object)biaozhunhuamubanVO.getChildren();
			if(biaozhunhuamubanVO.getChildren() != null && biaozhunhuamubanVO.getChildren().size()>0){
				parseMubanList(childrenList,aid,atier);
			}
		}
	}



	public void parseAbcdMubanList(List<SafetyProductionFileVO> mubanList, int upperid, String tier,Integer deptId) {
		for (SafetyProductionFileVO safetyProductionFileVO : mubanList) {
			Integer safeid = safetyProductionFileVO.getId();
			safetyProductionFileVO.setParentId(upperid);
			safetyProductionFileVO.setId(this.id);
			safetyProductionFileVO.setDeptId(this.deptId);
			safetyProductionFileVO.setSafeid(safeid);
			safetyProductionFileVO.setIs_muban(1);
			safetyProductionFileVO.setPath(StrUtil.replace(safetyProductionFileVO.getPath(),FilePathConstant.MUBAN_DEPT_NAME,this.deptName));
			String atier = tier+"-"+this.id;
			safetyProductionFileVO.setTier(atier);
			this.abcdList.add(safetyProductionFileVO);
			int aid = id;
			this.id++;
			List<SafetyProductionFileVO> childrenList = (List<SafetyProductionFileVO>)(Object)safetyProductionFileVO.getChildren();
			if(safetyProductionFileVO.getChildren() != null && safetyProductionFileVO.getChildren().size()>0){
				parseAbcdMubanList(childrenList,aid,atier,deptId);
			}
		}
	}

	public void close() {
    	this.id=null;
    	this.deptId=null;
    	this.deptName=null;
    	this.list=new ArrayList<Biaozhunhuamuban>();
    	this.abcdList=new ArrayList<SafetyProductionFile>();
	}

}
