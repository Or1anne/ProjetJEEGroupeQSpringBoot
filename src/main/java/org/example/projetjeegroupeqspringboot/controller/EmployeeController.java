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

            // Mise à jour des champs
            employee.setLastName(lastName);
            employee.setFirstName(firstName);
            employee.setGrade(grade);
            employee.setPost(post);
            employee.setSalary(salary != null ? salary : 0.0);

            // Gestion du département
            if (departmentId != null) {
                employee.setDepartment(departmentService.findById(Long.valueOf(departmentId)));
            }

            // Génération d'un username et password par défaut si nouveau
            if (id == null) {
                employee.setUsername(firstName.toLowerCase() + "." + lastName.toLowerCase());
                employee.setPassword("password"); // À changer avec un vrai système de mot de passe
            }

            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("success",
                id != null ? "Employé modifié avec succès" : "Employé ajouté avec succès");

            return "redirect:/employee";
        }
    }
