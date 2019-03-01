package com.kkwrite.jsp.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午6:19:26 
 * @version 1.0.0
 */
@Controller
public class JspCtrl {

	@RequestMapping("/jsp")
	public ModelAndView jsp() {
		ModelAndView mav = new ModelAndView("hello/hello");
		return mav;
	}
	
}
