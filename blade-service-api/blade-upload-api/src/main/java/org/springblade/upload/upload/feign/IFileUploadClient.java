package org.springblade.upload.upload.feign;

import org.springblade.upload.upload.entity.FileUpload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/** upload Feign接口类
 */
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-upload",
	fallback = IFileUploadClientBack.class
)
public interface IFileUploadClient {

	String API_PREFIX = "upload/uploadback";

	/**
	 * 获取附件
	 * @param attachcode
	 * @return
	 */
	@GetMapping(API_PREFIX + "/selectAll")
	List<FileUpload> selectAll(@RequestParam("attachcode") String attachcode);

	/**
	 * 获取附件
	 * @param filename
	 * @return
	 */
	@GetMapping(API_PREFIX + "/selectByFileName")
	FileUpload selectByFileName(@RequestParam("filename") String filename);

	/**
	 * 获取附件url 列表
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getUrl")
	String getUrl(@RequestParam("str") String str);
	/**
	 * 获取附件url 登录
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getUrlUrl")
	String getUrlUrl(@RequestParam("str") String str);
	/**
	 * 获取附件url 日报
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getUrllogin")
	String getUrllogin(@RequestParam("str") String str);

	/**
	 * 根据保存唯一名称删除
	 * @return
	 */
	@GetMapping(API_PREFIX + "/delByFileName")
	boolean delByFileName(@RequestParam("filename") String filename);

	/**
	 * 自定义编辑在实现服务里的参数也必须加上 @RequestBody ，不然获取的对象仍然没有值
	 * @param str
	 * @return
	 */
	@PostMapping(API_PREFIX + "/updateCorrelation")
	Boolean updateCorrelation(@RequestParam("str") String str, @RequestParam("correlation") String correlation);

}
