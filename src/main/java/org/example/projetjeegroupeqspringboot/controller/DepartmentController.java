package org.example.projetjeegroupeqspringboot.controller;

import org.example.projetjeegroupeqspringboot.entity.Department;
import org.example.projetjeegroupeqspringboot.service.DepartmentService;
import org.example.projetjeegroupeqspringboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "ListDepartment";
    }

    @GetMapping("/add")
    public String addDepartment(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("employees", employeeService.findAll());
        return "FormDepartment";
    }

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/{id}")
    public String viewDepartment(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
        return "ViewDepartment";
    }
}
