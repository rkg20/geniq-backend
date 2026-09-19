package com.geniq.content.model;

import jakarta.persistence.*;

@Entity
@Table(name = "testimonials")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String quote;

    private String name;
    private String role;
    private String audience;

    public Testimonial() {
    }

    public Testimonial(String quote, String name, String role, String audience) {
        this.quote = quote;
        this.name = name;
        this.role = role;
        this.audience = audience;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuote() { return quote; }
    public void setQuote(String quote) { this.quote = quote; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getAudience() { return audience; }
    public void setAudience(String audience) { this.audience = audience; }
}
