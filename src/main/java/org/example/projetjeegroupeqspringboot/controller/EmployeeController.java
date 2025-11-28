    package org.example.projetjeegroupeqspringboot.controller;

    import org.example.projetjeegroupeqspringboot.entity.Employee;
    import org.example.projetjeegroupeqspringboot.entity.enumeration.Grade;
    import org.example.projetjeegroupeqspringboot.service.DepartmentService;
    import org.example.projetjeegroupeqspringboot.service.EmployeeService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    @Controller
    @RequestMapping("/employee")
    public class EmployeeController {

        @Autowired
        private EmployeeService employeeService;
        @Autowired
        private DepartmentService departmentService;

        // Liste des employés
        @GetMapping
        public String listEmployees(Model model) {
            model.addAttribute("employees", employeeService.findAll());
            return "ListEmployee";
        }

        // Formulaire d'ajout
        @GetMapping("/add")
        public String addEmployee(Model model) {
            model.addAttribute("employee", new Employee());
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("grades", Grade.values());
            model.addAttribute("isEditMode", false);
            return "FormEmployee";
        }

        // Formulaire d'édition
        @GetMapping("/edit/{id}")
        public String showEditForm(@PathVariable Long id, Model model) {
            Employee employee = employeeService.findById(id);
            model.addAttribute("employee", employee);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("grades", Grade.values());
            model.addAttribute("isEditMode", true);
            return "FormEmployee";
        }

        // Vue
        @GetMapping("/view/{id}")
        public String viewEmployee(@PathVariable Long id, Model model) {
            model.addAttribute("employee", employeeService.findById(id));
            return "ViewEmployee";
        }

        // Suppression
        @GetMapping("/delete/{id}")
        public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Employé supprimé !");
            return "redirect:/employee";
        }

        // Sauvegarde
        @PostMapping("/save")
        public String saveEmployee(@ModelAttribute Employee employee) {
            employeeService.save(employee);
            return "redirect:/employee";
        }
    }
