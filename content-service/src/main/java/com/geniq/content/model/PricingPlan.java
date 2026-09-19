package com.geniq.content.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pricing_plans")
public class PricingPlan {

    @Id
    private String id;

    private String name;
    private String duration;
    private String price;

    @Column(length = 500)
    private String tagline;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "plan_features", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "feature")
    private List<String> features;

    private String cta;
    private boolean popular;

    public PricingPlan() {
    }

    public PricingPlan(String id, String name, String duration, String price, String tagline,
                       List<String> features, String cta, boolean popular) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.tagline = tagline;
        this.features = features;
        this.cta = cta;
        this.popular = popular;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }
    public String getCta() { return cta; }
    public void setCta(String cta) { this.cta = cta; }
    public boolean isPopular() { return popular; }
    public void setPopular(boolean popular) { this.popular = popular; }
}
