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
    public String addDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("isEditMode", false);
        return "FormDepartment";
    }

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@PathVariable Long id, Model model) {
        Department department = departmentService.findById(id);

        if (department == null) {
            return "redirect:/departments"; // sécurité
        }

        model.addAttribute("department", department);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("isEditMode", true);

        return "FormDepartment";
    }

    @PostMapping("/edit/{id}")
    public String updateDepartment(@PathVariable Long id, @ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/{id}")
    public String viewDepartment(@PathVariable Long id, Model model) {
        Department department = departmentService.findById(id);

        if (department == null) {
            return "redirect:/departments";
        }

        model.addAttribute("department", department);
        return "ViewDepartment";
    }
}
