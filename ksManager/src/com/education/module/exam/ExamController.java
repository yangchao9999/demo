package com.education.module.exam;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.education.domain.Exam;
import com.education.framework.base.BaseController;
import com.education.framework.baseModule.module.business.BusinessServices;
import com.education.framework.domain.SearchParams;
import com.education.framework.page.Page;
import com.education.framework.session.SessionHelper;
import com.education.framework.util.calendar.CalendarUtil;
import com.education.module.paper.PaperServices;
import com.education.module.resCourse.ResCourseServices;

@Controller
@RequestMapping("exam")
public class ExamController extends BaseController{

	@Autowired
	private ExamServices services;
	@Autowired
	private ResCourseServices resCourseServices;
	@Autowired
	private PaperServices paperServices;
	@Autowired
	private BusinessServices businessServices;
	
	@RequestMapping(value = "")
	public String list(Model model, SearchParams searchParams,Page page,ServletRequest request){
		List<Exam> list = services.find(searchParams,page);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("business", businessServices.findObject(SessionHelper.getInstance().getUser().getBusinessId()));
		model.addAttribute("searchParams", searchParams);
		return "/exam/examList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		Exam exam = new Exam();
		exam.setPassScore(60);
		exam.setExamBegintime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
		exam.setExamEndtime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
		model.addAttribute("exam", exam);
		model.addAttribute("courseListJson", resCourseServices.convertJson(resCourseServices.find()));
		model.addAttribute("action","create");
		model.addAttribute("tab", "1");
		return "/exam/examEdit";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(Exam exam, Model model,RedirectAttributes redirectAttributes) {
		int id = services.save(exam);
		exam.setId(id);
		//生成缓存试卷
		paperServices.buildExamCachePaper(exam);
		redirectAttributes.addFlashAttribute(MESSAGE, MESSAGE_SAVE_SUCCESS);
		redirectAttributes.addFlashAttribute(MESSAGE_STATE, "alert-success");
		return "redirect:/exam";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model,ServletRequest request) {
		Exam exam = services.findForObject(id);
		exam.setPracList(services.convertPracConfList(exam.getPracConf()));
		model.addAttribute("exam", exam);
		String json = resCourseServices.fillSelCourse(resCourseServices.find(), exam.getSelCourseArr());
		model.addAttribute("courseListJson", json);
		model.addAttribute("action", "update");
		model.addAttribute("tab", request.getParameter("tab") == null ? "1" : request.getParameter("tab"));
		return "/exam/examEdit";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Exam exam, RedirectAttributes redirectAttributes) {
//		String pracConfOld = services.findPracConf(exam.getId());
//		Exam examOld = services.findForObject(exam.getId());
//		String pracConfOld = examOld.getPracConf();
		services.update(exam);
		//生成缓存试卷
//		if(!pracConfOld.equals(exam.getPracConf()) || !"".equals(examOld.getMsg())){
		exam = services.findForObject(exam.getId());
		exam.setPracList(services.convertPracConfList(exam.getPracConf()));
		paperServices.buildExamCachePaper(exam);
//		}
		redirectAttributes.addFlashAttribute(MESSAGE, MESSAGE_UPDATE_SUCCESS);
		redirectAttributes.addFlashAttribute(MESSAGE_STATE, "alert-success");
		return "redirect:/exam";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) {
		services.delete(id);
		redirectAttributes.addFlashAttribute(MESSAGE, MESSAGE_DELETE_SUCCESS);
		redirectAttributes.addFlashAttribute(MESSAGE_STATE, "alert-success");
		return "redirect:/exam";
	}
	
	@RequestMapping(value = "findExamListAjax", method = RequestMethod.GET)
	@ResponseBody
	public List<String> findExamListAjax(@RequestParam(value="term",required=false) String name) {
		List<String> t = services.findExamNameList(name);
		return t;
	}
	
//	@RequestMapping(value = "reqCachePaper", method = RequestMethod.GET)
//	@ResponseBody
//	public ApiResult reqCachePaper(@RequestParam(value="examId",required=true) String examId) {
//		boolean r = services.batchCachePaper(examId);
//		ApiResult result = new ApiResult();
//		return result;
//	}
//	
//	@RequestMapping(value = "reqRemoveCachePaper", method = RequestMethod.GET)
//	@ResponseBody
//	public ApiResult reqRemoveCachePaper(@RequestParam(value="examId",required=true) String examId) {
//		boolean r = services.batchRemoveCachePaper(examId);
//		ApiResult result = new ApiResult();
//		return result;
//	}
}
