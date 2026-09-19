package com.geniq.content.bootstrap;

import com.geniq.content.model.Mentor;
import com.geniq.content.model.PricingPlan;
import com.geniq.content.model.Resource;
import com.geniq.content.model.Testimonial;
import com.geniq.content.repository.MentorRepository;
import com.geniq.content.repository.PricingPlanRepository;
import com.geniq.content.repository.ResourceRepository;
import com.geniq.content.repository.TestimonialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the in-memory database with the same content the frontend used to hold
 * as static data. This makes the API return real, DB-backed content.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final MentorRepository mentorRepository;
    private final PricingPlanRepository pricingPlanRepository;
    private final TestimonialRepository testimonialRepository;
    private final ResourceRepository resourceRepository;

    public DataSeeder(MentorRepository mentorRepository,
                      PricingPlanRepository pricingPlanRepository,
                      TestimonialRepository testimonialRepository,
                      ResourceRepository resourceRepository) {
        this.mentorRepository = mentorRepository;
        this.pricingPlanRepository = pricingPlanRepository;
        this.testimonialRepository = testimonialRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public void run(String... args) {
        seedMentors();
        seedPricing();
        seedTestimonials();
        seedResources();
    }

    private void seedMentors() {
        if (mentorRepository.count() > 0) return;
        mentorRepository.saveAll(List.of(
            new Mentor("aarti-menon", "Aarti Menon", "Communication Coach", "12 years", "Communication",
                List.of("Active listening", "Family dialogue", "Conflict resolution"),
                "Aarti helps families turn tense conversations into moments of genuine connection.",
                4.9, "https://images.unsplash.com/photo-1573497019940-1c28c88b4f3e?auto=format&fit=crop&w=400&q=70"),
            new Mentor("rohan-desai", "Rohan Desai", "Teen Development Mentor", "9 years", "Teen Development",
                List.of("Confidence building", "Peer pressure", "Identity"),
                "Rohan works with teenagers to build confidence and a strong sense of self.",
                4.8, "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=400&q=70"),
            new Mentor("priya-nair", "Priya Nair", "Parenting Guide", "15 years", "Parenting",
                List.of("Positive parenting", "Boundaries", "Emotional support"),
                "Priya supports parents in staying connected while guiding with empathy.",
                5.0, "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=400&q=70"),
            new Mentor("kabir-shah", "Kabir Shah", "Career & Future Mentor", "11 years", "Career",
                List.of("Goal setting", "Study balance", "Decision making"),
                "Kabir helps students and parents align on realistic, motivating future goals.",
                4.7, "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=70"),
            new Mentor("meera-iyer", "Meera Iyer", "Life Skills Facilitator", "8 years", "Life Skills",
                List.of("Emotional regulation", "Habits", "Resilience"),
                "Meera teaches practical everyday skills that make growing up easier.",
                4.9, "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&w=400&q=70"),
            new Mentor("sameer-khan", "Sameer Khan", "Communication Coach", "10 years", "Communication",
                List.of("Difficult conversations", "Empathy", "Trust building"),
                "Sameer specialises in rebuilding trust through honest, structured dialogue.",
                4.8, "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?auto=format&fit=crop&w=400&q=70")
        ));
    }

    private void seedPricing() {
        if (pricingPlanRepository.count() > 0) return;
        pricingPlanRepository.saveAll(List.of(
            new PricingPlan("starter", "Starter", "4 Weeks", "\u20B94,999",
                "A gentle first step toward understanding.",
                List.of("Interactive sessions", "Weekly assessments", "Progress tracking", "Parent resources"),
                "Get Started", false),
            new PricingPlan("grow", "Grow", "8 Weeks", "\u20B98,999",
                "The balanced journey most families choose.",
                List.of("Interactive sessions", "Weekly assessments", "1-on-1 guidance", "Progress tracking", "Parent + student resources"),
                "Start Your Journey", true),
            new PricingPlan("complete", "Complete", "12 Weeks", "\u20B913,999",
                "The full three-stage transformation.",
                List.of("Full 3-stage journey", "Interactive sessions", "Weekly assessments", "Multiple 1-on-1 sessions",
                    "Personalized growth plan", "Progress tracking", "Parent + student support"),
                "Explore Complete Program", false)
        ));
    }

    private void seedTestimonials() {
        if (testimonialRepository.count() > 0) return;
        testimonialRepository.saveAll(List.of(
            new Testimonial("For the first time in years, my daughter and I actually talk instead of argue. GenIQ gave us a shared language.",
                "Anjali R.", "Parent of a 15-year-old", "parent"),
            new Testimonial("The sessions helped me explain how I feel without it turning into a fight. My parents finally get me a little more.",
                "Ved P.", "Student, age 16", "student"),
            new Testimonial("The weekly assessments were simple but powerful. We could see our progress week after week.",
                "Farhan & Nadia", "Parents", "parent"),
            new Testimonial("I used to feel unheard at home. Now I have the confidence to start conversations myself.",
                "Ishita M.", "Student, age 14", "student")
        ));
    }

    private void seedResources() {
        if (resourceRepository.count() > 0) return;
        resourceRepository.saveAll(List.of(
            new Resource("Communication", "How to Listen So Your Teen Actually Opens Up",
                "Small shifts in how we listen can completely change how heard our children feel.",
                "https://images.unsplash.com/photo-1516534775068-ba3e7458af70?auto=format&fit=crop&w=600&q=70"),
            new Resource("Parenting", "Setting Boundaries Without Breaking Connection",
                "Boundaries and warmth can co-exist. Here is how to hold both at the same time.",
                "https://images.unsplash.com/photo-1476703993599-0035a21b17a9?auto=format&fit=crop&w=600&q=70"),
            new Resource("Teen Development", "Understanding the Teenage Brain",
                "Why teens react the way they do \u2014 and how understanding it builds patience.",
                "https://images.unsplash.com/photo-1531482615713-2afd69097998?auto=format&fit=crop&w=600&q=70"),
            new Resource("Career", "Talking About the Future Without Pressure",
                "Turn career conversations into collaboration instead of expectation.",
                "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?auto=format&fit=crop&w=600&q=70"),
            new Resource("Life Skills", "Everyday Habits That Build Confidence",
                "Confidence is built in small daily moments. Here are the ones that matter.",
                "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=600&q=70"),
            new Resource("Family", "Creating Rituals That Bring Families Closer",
                "Simple, repeatable moments that quietly strengthen family bonds over time.",
                "https://images.unsplash.com/photo-1609220136736-443140cffec6?auto=format&fit=crop&w=600&q=70")
        ));
    }
}
