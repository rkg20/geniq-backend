package com.geniq.content.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "mentors")
public class Mentor {

    @Id
    private String id;

    private String name;
    private String role;
    private String experience;
    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mentor_expertise", joinColumns = @JoinColumn(name = "mentor_id"))
    @Column(name = "skill")
    private List<String> expertise;

    @Column(length = 1000)
    private String bio;

    private double rating;

    @Column(length = 600)
    private String image;

    public Mentor() {
    }

    public Mentor(String id, String name, String role, String experience, String category,
                  List<String> expertise, String bio, double rating, String image) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.experience = experience;
        this.category = category;
        this.expertise = expertise;
        this.bio = bio;
        this.rating = rating;
        this.image = image;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<String> getExpertise() { return expertise; }
    public void setExpertise(List<String> expertise) { this.expertise = expertise; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
