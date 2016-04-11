package com.legolas;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/*
import com.legolas.config.CROSSInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
*/

@SpringBootApplication
@RestController
public class PatrolSystemApplication /*extends WebMvcConfigurerAdapter*/ {
	static Logger Log = Logger.getLogger(PatrolSystemApplication.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String greeting() {
		return "Hello Spring!";
	}

	public static void main(String[] args) {
		SpringApplication.run(PatrolSystemApplication.class, args);
		Log.info("启动成功！");
	}
/*
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CROSSInterceptor()).addPathPatterns("/");
	}*/
}
