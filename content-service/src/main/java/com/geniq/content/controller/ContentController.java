package com.geniq.content.controller;

import com.geniq.content.model.Mentor;
import com.geniq.content.model.PricingPlan;
import com.geniq.content.model.Resource;
import com.geniq.content.model.Testimonial;
import com.geniq.content.repository.MentorRepository;
import com.geniq.content.repository.PricingPlanRepository;
import com.geniq.content.repository.ResourceRepository;
import com.geniq.content.repository.TestimonialRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentController {

    private final MentorRepository mentorRepository;
    private final PricingPlanRepository pricingPlanRepository;
    private final TestimonialRepository testimonialRepository;
    private final ResourceRepository resourceRepository;

    public ContentController(MentorRepository mentorRepository,
                             PricingPlanRepository pricingPlanRepository,
                             TestimonialRepository testimonialRepository,
                             ResourceRepository resourceRepository) {
        this.mentorRepository = mentorRepository;
        this.pricingPlanRepository = pricingPlanRepository;
        this.testimonialRepository = testimonialRepository;
        this.resourceRepository = resourceRepository;
    }

    @GetMapping("/mentors")
    public List<Mentor> mentors(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank() && !category.equalsIgnoreCase("All")) {
            return mentorRepository.findByCategoryIgnoreCase(category);
        }
        return mentorRepository.findAll();
    }

    @GetMapping("/mentors/{id}")
    public Mentor mentor(@PathVariable String id) {
        return mentorRepository.findById(id).orElse(null);
    }

    @GetMapping("/pricing")
    public List<PricingPlan> pricing() {
        return pricingPlanRepository.findAll();
    }

    @GetMapping("/testimonials")
    public List<Testimonial> testimonials() {
        return testimonialRepository.findAll();
    }

    @GetMapping("/resources")
    public List<Resource> resources() {
        return resourceRepository.findAll();
    }
}
