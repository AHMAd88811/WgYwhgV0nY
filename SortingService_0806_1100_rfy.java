// 代码生成时间: 2025-08-06 11:00:07
package com.yourcompany.services;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.ok;

// SortingService class that handles sorting
public class SortingService extends Controller {

    // Method to sort an array of integers using bubble sort algorithm
    public static int[] bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is null or empty.");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    // Method to sort a list of integers using Java Collections
    public static List<Integer> sortList(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List is null or empty.");
        }
        Collections.sort(list);
        return list;
    }

    // Action method to handle GET request and return sorted array
    public Result sortArray() {
        try {
            // Example array
            int[] exampleArray = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
            // Sort the array
            int[] sortedArray = bubbleSort(exampleArray);
            // Convert array to JSON response
            return ok(Arrays.toString(sortedArray));
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        }
    }

    // Action method to handle GET request and return sorted list
    public Result sortList() {
        try {
            // Example list
            List<Integer> exampleList = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
            // Sort the list
            List<Integer> sortedList = sortList(exampleList);
            // Convert list to JSON response
            return ok(sortedList.toString());
        } catch (IllegalArgumentException e) {
            return badRequest(e.getMessage());
        }
    }
}
