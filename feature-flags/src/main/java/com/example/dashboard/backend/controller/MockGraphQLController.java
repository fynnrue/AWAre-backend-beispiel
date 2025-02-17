package com.example.dashboard.backend.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MockGraphQLController {

    private Map<String, Feature> features = new HashMap<>();
    private Map<String, List<Integer>> excludedCourses = new HashMap<>();

    public MockGraphQLController() {
        createFeatureMap();
    }

    private void createFeatureMap() {
        features.put("dashboard-addition", new Feature("dashboard-addition", true, "bsp"));
        features.put("teacher-dashboard", new Feature("teacher-dashboard", true, "aware"));
        features.put("bsp-feature1", new Feature("bsp-feature1", true, "aware"));
        features.put("bsp-feature2", new Feature("bsp-feature2", true, "aware"));
        features.put("bsp-feature3", new Feature("bsp-feature3", true, "aware"));
    }

    @QueryMapping
    public Feature getFeature(@Argument String name, @Argument @Nullable Integer courseId) {
        Feature feature = features.get(name);
        if (feature == null) {
            return null;
        }

        if (courseId != null) {
            List<Integer> excludedIds = excludedCourses.get(name);
            if (excludedIds != null && excludedIds.contains(courseId)) {
                return new Feature(feature.name, false, feature.application);
            }
        }

        return feature;
    }


    @QueryMapping
    public List<Feature> getAllFeatures(@Argument String application) {
        return features.values().stream()
                .filter(feature -> feature.application().equals(application))
                .collect(Collectors.toList());
    }

    @MutationMapping
    public Feature setFeature(@Argument FeatureInput feature) {
        Feature oldFeature = features.get(feature.name);

        Feature newFeature = new Feature(feature.name, feature.status, oldFeature.application);
        features.put(feature.name(), newFeature);

        return newFeature;
    }

    @QueryMapping
    public List<Integer> getExcludedCourses(@Argument String featureName) {
        return excludedCourses.getOrDefault(featureName, new ArrayList<>());
    }

    @MutationMapping
    public List<Integer> excludeCourseFromFeature(@Argument String featureName, @Argument Integer courseId) {
        excludedCourses.computeIfAbsent(featureName, k -> new ArrayList<>()).add(courseId);
        return excludedCourses.get(featureName);
    }

    @MutationMapping
    public List<Integer> includeCourseInFeature(@Argument String featureName, @Argument Integer courseId) {
        excludedCourses.getOrDefault(featureName, new ArrayList<>()).remove(courseId);
        return excludedCourses.get(featureName);
    }

    public record Feature(String name, boolean status, String application) {}

    public record FeatureInput(String name, boolean status) {}
}
