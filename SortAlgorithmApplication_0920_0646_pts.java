// 代码生成时间: 2025-09-20 06:46:22
import play.mvc.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Controller class that handles sorting algorithm requests.
 */
public class SortAlgorithmApplication extends Controller {

    /**
     * Action method to sort a list of integers using the specified algorithm.
     * @param request HTTP request containing the list to sort and algorithm to use.
     * @return A sorted list of integers.
     */
    public static Result sortList(String list, String algorithm) {
        try {
            // Convert the incoming list string to an integer array.
            int[] numbers = Arrays.stream(list.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

            // Select and sort the array using the chosen algorithm.
            switch (algorithm.toLowerCase()) {
                case "bubble":
                    Collections.sort(Arrays.asList(numbers));
                    break;
                case "selection":
                    // Implementation of the selection sort algorithm.
                    selectionSort(numbers);
                    break;
                case "insertion":
                    // Implementation of the insertion sort algorithm.
                    insertionSort(numbers);
                    break;
                default:
                    return badRequest("Invalid algorithm specified.");
            }

            // Convert the sorted integer array back to a string and return it.
            return ok(Arrays.toString(numbers).replaceAll("\[|\]",""));
        } catch (Exception e) {
            // Catch and return any errors that occur during sorting.
            return internalServerError("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Selection sort algorithm.
     * @param numbers Array of integers to sort.
     */
    private static void selectionSort(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] < numbers[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the minimum element with the first element.
            int temp = numbers[minIndex];
            numbers[minIndex] = numbers[i];
            numbers[i] = temp;
        }
    }

    /**
     * Insertion sort algorithm.
     * @param numbers Array of integers to sort.
     */
    private static void insertionSort(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int current = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] > current) {
                numbers[j + 1] = numbers[j];
                j--;
            }
            numbers[j + 1] = current;
        }
    }
}