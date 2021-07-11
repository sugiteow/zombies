package com.ailo.zombies.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class CustomMatchers {

    public static <T> Matcher<Collection<? extends T>> onlyHasItems(T... expectedItems) {
        return new TypeSafeMatcher<Collection<? extends T>>() {

            @Override
            protected boolean matchesSafely(Collection<? extends T> actualItems) {
                return hasSize(expectedItems.length).matches(actualItems)
                        && hasItems(expectedItems).matches(actualItems);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
