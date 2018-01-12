package com.example.androidstarter.data.database;

import com.example.androidstarter.data.models.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by samvedana on 19/12/17.
 */

class DataGenerator {

    private static final List<String> DESCRIPTIONS = new ArrayList<>(Arrays.asList(
            new String[] {
                    "Write a post", "Daily Korean practice", "Email two friends", "Clean Home"}
    ));
    private static final List<Long> ESTIMATES = new ArrayList<Long>(Arrays.asList(
            new Long[] {10L, 15L, 5L, 30L}
    ));

    static List<Task> generateTasks() {
        List<Task> tasks = new ArrayList<>();
        Random rnd = new Random();
        for (String desc : DESCRIPTIONS) {
            int i = rnd.nextInt(ESTIMATES.size());
            long est = ESTIMATES.remove(i);
            tasks.add(new Task(desc, est));
        }
        return tasks;
    }

}
