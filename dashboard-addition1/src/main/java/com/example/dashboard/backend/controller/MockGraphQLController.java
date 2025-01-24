package com.example.dashboard.backend.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class MockGraphQLController {

    private final List<Item> mockItems = Arrays.asList(
            new Item(1L, "Mikroskop", "Laborgeräte"),
            new Item(2L, "Schutzbrille", "Schutzausrüstung"),
            new Item(3L, "Reagenzglas", "Laborgeräte"),
            new Item(4L, "Bunsenbrenner", "Laborgeräte"),
            new Item(5L, "Präparierbesteck", "Laborwerkzeuge"),
            new Item(6L, "Laborkittel", "Schutzausrüstung")
    );

    @QueryMapping
    public List<Item> getItems() {
        return mockItems;
    }

    record Item(Long id, String name, String group) {}

}

