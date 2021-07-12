package com.ailo.zombies.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
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
                description.appendText(Arrays.toString(expectedItems));
            }
        };
    }

    public static <T> TypeSafeMatcher<Collection<? extends T>> onlyHasItemsInOrder(T... expectedItems) {

        return new TypeSafeMatcher<Collection<? extends T>>() {
            @Override
            protected boolean matchesSafely(Collection<? extends T> actualItems) {
                boolean matches = actualItems.size() == asList(expectedItems).size();

                List<T> actualItemsList = new ArrayList<>(actualItems);

                for (int index = 0; index < expectedItems.length; index++) {
                    matches = matches && is(actualItemsList.get(index)).matches(expectedItems[index]);
                }

                return matches;
            }

            @Override
            public void describeTo(Description description) {
                String expectedItemsString = stream(expectedItems)
                        .map(Object::toString)
                        .collect(joining(","));

                description.appendText("Containing only [" + expectedItemsString + "] in order");
            }
        };
    }
}
