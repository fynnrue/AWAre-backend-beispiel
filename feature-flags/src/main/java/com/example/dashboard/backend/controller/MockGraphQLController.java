package com.example.dashboard.backend.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MockGraphQLController {

    private Feature dashboardFeature;

    public MockGraphQLController() {
        this.dashboardFeature = new Feature("dashboard-addition", true);
    }

    @QueryMapping
    public Feature getDashboardFeature() {
        return dashboardFeature;
    }

    @MutationMapping
    public Feature setDashboardFeature(@Argument FeatureInput feature) {
        Feature f = new Feature(feature.name, feature.status);
        this.dashboardFeature = f;

        System.out.println(dashboardFeature.status);

        return this.dashboardFeature;
    }

    public record Feature(String name, boolean status) {}

    public record FeatureInput(String name, boolean status) {}
}
