package com.example.dashboard.backend.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MockGraphQLController {

    private Map<String, Feature> features;

    public MockGraphQLController() {
        this.features = new HashMap<>();
        this.features.put("dashboard-addition", new Feature("dashboard-addition", true));
        this.features.put("teacher-dashboard", new Feature("teacher-dashboard", true));
        this.features.put("bsp-feature1", new Feature("bsp-feature1", true));
        this.features.put("bsp-feature2", new Feature("bsp-feature2", true));
        this.features.put("bsp-feature3", new Feature("bsp-feature3", true));
    }

    @QueryMapping
    public Feature getFeature(@Argument String name) {
        return features.get(name);
    }

    @QueryMapping
    public List<Feature> getAllFeatures() {
        return new ArrayList<>(features.values());
    }

    @MutationMapping
    public Feature setFeature(@Argument FeatureInput feature) {
        Feature f = new Feature(feature.name(), feature.status());
        features.put(feature.name(), f);

        System.out.println("Status aktualisiert: " + f.name() + " -> " + f.status());

        return f;
    }

    public record Feature(String name, boolean status) {}

    public record FeatureInput(String name, boolean status) {}
}
