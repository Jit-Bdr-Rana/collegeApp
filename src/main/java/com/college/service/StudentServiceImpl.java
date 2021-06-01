package com.college.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.college.model.Student;
import com.college.repository.StudentRepository;



@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository stuRepo;
	 


	@Override
	public void saveStudent(Student student) {
	    this.stuRepo.save(student);
	}

	@Override
	public List<Student> getAllStudent() {
	  return this.stuRepo.findAll();
		
	}

	@Override
	public Student getStudentById(Integer id) {
		return stuRepo.getById(id);
	}

	@Override
	public Page<Student> fetchStudentByYearAndProgram(int pageNo, int pageSize, String sortField, String sortDirection,String year, Integer program_id) {
		
		Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pageNo-1,pageSize,sort);
	
		return stuRepo.getStudentByYearAndProgram(year,program_id,pageable);
		
	}
   public Page<Student> findPaginate(int pageNo, int pageSize, String sortField, String sortDirection) {
		
		Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pageNo-1,pageSize,sort);
		return this.stuRepo.findAll(pageable);
	}

@Override
public void deleteStudentById(Integer id) {
	
	this.stuRepo.deleteById(id);
}



@Override
public Student fetchStudentFromContact(String contact) {
	// TODO Auto-generated method stub
	return this.stuRepo.getStudentByContact(contact);
}

}
