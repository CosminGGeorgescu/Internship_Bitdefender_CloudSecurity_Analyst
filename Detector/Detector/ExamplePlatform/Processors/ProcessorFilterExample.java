package Detector.ExamplePlatform.Processors;

import Detector.ExamplePlatform.Pojos.UserClassExample;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProcessorFilterExample implements Predicate<UserClassExample> {

    // Each Processor can have its own "memory".
    // Beware that in a real world scenario you will have massive amount of data and storing is expensive.
    final private List<String> filterMemory;

    public ProcessorFilterExample() {
        filterMemory = new ArrayList<>();
    }

    @Override
    public boolean test(UserClassExample userClassExample) {
        // A simple test condition.
        // See if the current username was previously processed. If so, remove it from the stream.
        if (!filterMemory.contains(userClassExample.getUserName())) {
            // Add the current username to the list, return true as this is a new username,
            // and it should remain in the stream
            filterMemory.add(userClassExample.getUserName());
            return true;
        }

        // Otherwise, the current user is a duplicate and should be filtered out
        return false;
    }
}
