package org.springblade.upload.upload.service.impl;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.StringUtil;
import org.springblade.upload.upload.entity.FileUpload;
import org.springblade.upload.upload.mapper.FileUploadMapper;
import org.springblade.upload.upload.service.FileUploadService;
import org.springblade.common.configurationBean.FileServer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: SafetyStandards
 * @description: FileUploadServiceImpl
 **/
@Service
@AllArgsConstructor
public class FileUploadServiceImpl extends ServiceImpl<FileUploadMapper, FileUpload> implements FileUploadService {

	private FileUploadMapper fileUploadMapper;

	private FileServer fileServer;

	@Override
	public FileUpload selectByFileName(String filename) {
		return fileUploadMapper.selectByFileName(filename);
	}

	@Override
	public List<FileUpload> selectAll(String attachcode) {
		return fileUploadMapper.selectAll(attachcode);
	}

	@Override
	public List<FileUpload> huixian(String attachcode){
		List<FileUpload> list =  null;
		if(attachcode != null && !attachcode.equals("")){
			list =  fileUploadMapper.selectAll(attachcode);
		}
		return list;
	}

	@Override
	public boolean insertAttachfile(FileUpload FileUpload) {
		return fileUploadMapper.insertAttachfile(FileUpload);
	}

	@Override
	public String getUrl(String str) {
		List<String> list = StrSpliter.split(str, ',', 0, true, true);
//		String log="[";
		String log="";
		for (int i = 0; i <list.size() ; i++) {
			FileUpload files=fileUploadMapper.selectByFileName(list.get(i));
			String ids= files.getId();
			String url= StrUtil.replace(fileServer.getUrlPrefix()+ FilePathConstant.EnclosureUrl+files.getPath(),"\\","/");
			String name= files.getFileName();
			String savename= files.getFileSaveName();
			if(i==0){
//				log=log+"{\"name\":\""+name+"\",\"url\":\""+url+"\",\"id\":\""+ids+"\",\"savename\":\""+savename+"\"}";
				log+=url+",";
			}else{
				log+=url+",";
//				log=log+",{\"name\":\""+name+"\",\"url\":\""+url+"\",\"id\":\""+ids+"\",\"savename\":\""+savename+"\"}";
			}
		}
		//先获取最后一个  - 所在的位置
		int indexs = log.lastIndexOf(",");
		//获取从0到最后一个 / 之间的字符
		String str3 = log.substring(0, indexs);
		log = str3;
		return log;
	}

	@Override
	public String getUrlUrl(String str) {
		List<String> list = StrSpliter.split(str, ',', 0, true, true);
		String url="";
		for (int i = 0; i <list.size() ; i++) {
			FileUpload files=fileUploadMapper.selectByFileName(list.get(i));
			url= StrUtil.replace(fileServer.getUrlPrefix()+FilePathConstant.EnclosureUrl+files.getPath(),"\\","/");
		}
		return url;
	}

	@Override
	public String getUrllogin(String str) {
		List<String> list = StrSpliter.split(str, ',', 0, true, true);
		String url="";
		for (int i = 0; i <list.size() ; i++) {
			FileUpload files=fileUploadMapper.selectByFileName(list.get(i));
			url= StrUtil.replace(FilePathConstant.EnclosureUrl+files.getPath(),"\\","/");
		}
		return url;
	}

	@Override
	public boolean updateCorrelation(FileUpload FileUpload) {
		return fileUploadMapper.updateCorrelation(FileUpload);
	}

	@Override
	public boolean delByFileName(String filename) {
		List<String> split = StrSpliter.split(filename, ',', 0, true, true);
		return fileUploadMapper.delByFileName(split.get(0));
	}

	@Override
	public boolean updateCorr(String str,String correlation) {
		boolean flag=false;
		List<String> list = StrSpliter.split(str, ',', 0, true, true);
		for (int i = 0; i <list.size() ; i++) {
			FileUpload files=fileUploadMapper.selectByFileName(list.get(i));
			FileUpload FileUpload=new FileUpload();
			FileUpload.setId(files.getId());
			FileUpload.setCorrelation(correlation);
			flag=fileUploadMapper.updateCorrelation(FileUpload);
		}
		return flag;
	}

//	@Override
//	public List<FileUpload> selectByNotSaveName(String attachcode, List<String> str) {
//		return fileUploadMapper.selectByNotSaveName(attachcode,str);
//	}
}
