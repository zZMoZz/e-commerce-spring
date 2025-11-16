package com.mohsenko.e_commerce_mohsenko.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.text.Normalizer;

public final class SlugUtil {
    private SlugUtil() {}

    public static String toSlug(String input, SlugChecker checker) {
        // check the input
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // create a slug
        String baseSlug = createSlug(input);

        // check the slug uniqueness
        String slug = baseSlug;
        int counter = 1;
        while(checker.exists(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        // return it
        return slug;
    }

     private static String createSlug(String input) {
        // normalize to NFD form (decompose accented characters)
        input = Normalizer.normalize(input, Normalizer.Form.NFD);

        // transform to slug
        input = input
                // 1. trim the input
                .trim()
                // 2. lowercase the input
                .toLowerCase()
                // 3. remove all accents and diacritics resulting from normalization process
                .replaceAll("\\p{M}", "")
                // 4. remove non-alphanumeric chars
                .replaceAll("[^a-z0-9\\s-]", "")
                // 5. convert spaces to hyphens
                .replaceAll("\\s+", "-")
                // 6. collapse multiple hyphens
                .replaceAll("-+", "-");

        // handle slug if become empty after these operations
        return (input.isBlank()) ? "item" : input;
    }

    /* this works like a contract that says: I need something that tell me
     * if a slug exists. I don't care HOW you check it, just give me true/false.
     * We pass something like that to `toSlug()` function: toSlug("Apple", brandRepository::existsBySlug);
     * Java translates it: SlugChecker checker = (slug) -> brandRepository.existsBySlug(slug); */

    /*
     * We do that because I wanted to can pass any repo, by receive an attribute with `JpaRepository` type.
     * That was working, but I wanted to use `existsBySlug()` method and it is a custom method we create in repos
     * not a method `JpaRepository` provides, so we can't call it, so I didn't use this way. I used functional interface
     * that accept any method returns boolean value and takes string value. */
    @FunctionalInterface
    public interface SlugChecker {
        boolean exists(String slug);
    }
}
