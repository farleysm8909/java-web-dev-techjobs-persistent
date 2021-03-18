package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll()); /* Part 3: update home controller #2. Populates employer dropdown on /add, variable "employers" found in templates/add.html */
        return "add";
    }

    // students must remove last param if want to see data in table after finishing part 3 #4
    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId) { //@RequestParam List<Integer> skills
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            //model.addAttribute("employers", employerRepository.findAll()); /* Added this too, for when form fails */
            return "add";
        }
        Employer selectedEmployer = employerRepository.findById(employerId).orElse(new Employer());        /* Part 3: #4, need .orElse or get Optional error */
        newJob.setEmployer(selectedEmployer); /* tricky: set employer field of Job object, then save newJob to repo/database */

        jobRepository.save(newJob);
        return "redirect:";
    }

    /* Unexplained work to do in part 3 in order to view saved jobs under /view/{jobId}
     * this method was originally empty save for return value
     * tip: look at displayViewEmployer and Skill for hints */
    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job); // must be named "job" per view.html
            return "view";
        } else {
            return "redirect:/";
        }
    }

    /* From EmployerController method:
        Optional optEmployer = employerRepository.findById(employerId); // added second half of this, part 2: controllers
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    */

}
