package org.springblade.doc.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author hyp
 * @create 2022-09-22 21:50
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class UploadConfig {

	@Bean(name="multipartResolver")
	public MultipartResolver multipartResorver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setResolveLazily(true);
		resolver.setMaxInMemorySize(40960);
		//上传文件大小
		resolver.setMaxUploadSize(5 * 1024 * 1024);
		return resolver;
	}

}
