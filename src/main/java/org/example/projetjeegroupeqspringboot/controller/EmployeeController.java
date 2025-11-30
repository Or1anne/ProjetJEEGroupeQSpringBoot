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

    import java.util.List;

    @Controller
    @RequestMapping("/employee")
    public class EmployeeController {

        @Autowired
        private EmployeeService employeeService;
        @Autowired
        private DepartmentService departmentService;

        // Liste des employés
        @GetMapping
        public String listEmployees(@RequestParam(required = false) String searchCriteria, @RequestParam(required = false) String value, Model model) {

            if (searchCriteria == null || searchCriteria.isEmpty() || value == null || value.isEmpty()) {
                model.addAttribute("employees", employeeService.findAll());
                return "ListEmployee";
            }

            try {
                List<Employee> results = employeeService.search(searchCriteria, value);
                model.addAttribute("employees", results);
                return "ListEmployee";

            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "Critère de recherche invalide");
                return "ListEmployee";
            }
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
        public String saveEmployee(
                @RequestParam(required = false) Long id,
                @RequestParam("lastname") String lastName,
                @RequestParam("firstname") String firstName,
                @RequestParam("grade") Grade grade,
                @RequestParam(value = "post", required = false) String post,
                @RequestParam(value = "salary", required = false) Double salary,
                @RequestParam(value = "departmentId", required = false) Integer departmentId,
                RedirectAttributes redirectAttributes) {

            Employee employee;

            // Si id existe, c'est une modification
            if (id != null) {
                employee = employeeService.findById(id);
                if (employee == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Employé non trouvé");
                    return "redirect:/employee";
                }
            } else {
                // Sinon, c'est une création
                employee = new Employee();
            }

            employee.setLastName(capitalizeFirstLetter(lastName));
            employee.setFirstName(capitalizeFirstLetter(firstName));
            employee.setGrade(grade);
            employee.setPost(post);
            employee.setSalary(salary != null ? salary : 0.0);

            // Gestion du département
            if (departmentId != null) {
                employee.setDepartment(departmentService.findById(Long.valueOf(departmentId)));
            }

            // Génération automatique du username et password pour un nouvel employé
            if (id == null) {
                String username = employeeService.generateUniqueUsername(firstName, lastName);
                employee.setUsername(username);
                employee.setPassword(employeeService.getDefaultPassword());
            }

            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("success",
                id != null ? "Employé modifié avec succès" : "Employé ajouté avec succès");

            return "redirect:/employee";
        }

        /**
         * Méthode utilitaire pour formater un nom/prénom :
         * première lettre en majuscule, reste en minuscule
         */
        private String capitalizeFirstLetter(String str) {
            if (str == null || str.isEmpty()) {
                return str;
            }
            str = str.trim();
            if (str.isEmpty()) {
                return str;
            }
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }
