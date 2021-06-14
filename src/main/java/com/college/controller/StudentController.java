package com.college.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.college.model.Program;
import com.college.model.Student;
import com.college.model.User;
import com.college.model.Year;
import com.college.service.ProgramService;
import com.college.service.StudentService;
import com.college.service.UserService;
import com.college.service.YearService;



@Controller
@RequestMapping("/admin/")
public class StudentController {

@Autowired
private StudentService studentService;

@Autowired
private ProgramService programService;

@Autowired
private UserService userService;

@Autowired
private YearService yearService;

@GetMapping("/student/form")
public String showStudentForm(Model model) {
	String stu_link="active";
	List<Program> listPrograms = programService.showAllProgram();
	List<Year> listYears = yearService.getYear();
	model.addAttribute("stu_link",stu_link);
	model.addAttribute("student",new Student());
	model.addAttribute("listPrograms",listPrograms);
	model.addAttribute("listYears",listYears);
	
	return "admin/student_form";
}
@GetMapping("/student")
public String showStudentTable(Model model,HttpServletRequest response) {
	
	return findPaginated(1,"firstName","asc",model,response);
	
//	List<Student> listStudents =studentService.getAllStudent();
//	List<Program> listPrograms = programService.showAllProgram();
//	String stu_link="active";
//	model.addAttribute("stu_link",stu_link);
//	model.addAttribute("listStudents",listStudents);
//	model.addAttribute("listPrograms",listPrograms);
//	return "admin/student_table";
			
}
@PostMapping("/student/save")
public String saveStudent(Student student,RedirectAttributes redirAttrs, HttpServletRequest request) {
	
	  
	 
        student.getUser().setRole("student");
        student.setRegistrationYear("2020");
	 
	  if(student.getId()==null)
	  {
		  redirAttrs.addFlashAttribute("success", "Student has been added Successfully!.");  
	  }else {
		  
		
		  Integer page =Integer.parseInt( request.getParameter("page"));
		   String sortDir=request.getParameter("sortDir");
		   String year=request.getParameter("year");
		   String program=request.getParameter("programs");
		   String sortField=request.getParameter("sortField");
		   this.studentService.saveStudent(student);
		  redirAttrs.addFlashAttribute("success", "Student has been Updated Successfully!.");
		  return "redirect:/admin/page/"+page+"?sortField="+sortField+"&sortDir="+sortDir+"&year="+year+"&program="+program;
	  }
	  this.studentService.saveStudent(student);
	 
	return "redirect:/admin/student";
	
}

@GetMapping("/student/update/{id}")
 public String updateStudent(@PathVariable("id") Integer id,Model model,@RequestParam("sortField") String sortField,@RequestParam("page") int page,@RequestParam("sortDir") String sortDir,@RequestParam("year") String year,@RequestParam("program") String program ) {
	
	Student student=studentService.getStudentById(id);
	List<Program> listPrograms = programService.showAllProgram();
	List<Year> listYears = yearService.getYear();
	String stu_link="active";
	model.addAttribute("stu_link",stu_link);
	model.addAttribute("student",student);
	model.addAttribute("sortField",sortField);
	model.addAttribute("sortDir",sortDir);
	model.addAttribute("page",page);
	model.addAttribute("program",program);
	model.addAttribute("year",year);
	
	model.addAttribute("listYears",listYears);
	model.addAttribute("listPrograms",listPrograms);
	
	return "admin/student_form";
}


@GetMapping("/page/{pageNo}")
public String findPaginated(@PathVariable(value="pageNo")int pageNo ,@RequestParam("sortField") String sortField,@RequestParam("sortDir") String sortDir,Model model,HttpServletRequest response) {
	int pageSize=2;
    List<Student> listStudents;
    Page<Student> page;
	String year=response.getParameter("year");
	String program=response.getParameter("program");
	
	
	
	if(year==null|| program==null || program.equals("null") ) {
	
		 page=studentService.findPaginate(pageNo, pageSize,"firstName","asc");
		 listStudents=page.getContent();
		
			
	}else {
		
		 page =studentService.fetchStudentByYearAndProgram(pageNo, pageSize,"firstName","asc",Integer.parseInt(year),Integer.parseInt(program));
		 listStudents=page.getContent();
		 
		 model.addAttribute("year_id",Integer.parseInt(year));
		 model.addAttribute("program_id",Integer.parseInt(program));
	}
	
	
	String stu_link="active";
	List<Program> listPrograms = programService.showAllProgram();
	List<Year> listYears = yearService.getYear();
	model.addAttribute("stu_link",stu_link);
	
	model.addAttribute("listyears",listYears);
	model.addAttribute("listprograms",listPrograms);
	model.addAttribute("currentPage",pageNo);
	model.addAttribute("totalPages", page.getTotalPages());
	model.addAttribute("totalItems",page.getTotalElements());
	model.addAttribute("listStudents",listStudents);
	model.addAttribute("sortField",sortField);
	model.addAttribute("sortDir",sortDir);
	model.addAttribute("pageSize",pageSize);
	model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
	return "admin/student_table";
}
  
  @GetMapping("/student/delete/{id}")
  public String  deleteStudent(@PathVariable(value="id") Integer id,RedirectAttributes redirAttrs,@RequestParam("sortField") String sortField,@RequestParam("page") int page,@RequestParam("sortDir") String sortDir,@RequestParam("year") String year,@RequestParam("program") String program) {
	  this.studentService.deleteStudentById(id);
	  redirAttrs.addFlashAttribute("success", "Student  has been Deleted Successfully!.");
	  return "redirect:/admin/page/"+page+"?sortField="+sortField+"&sortDir="+sortDir+"&year="+year+"&program="+program;
  }
  
     
  

}
