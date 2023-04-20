package Functions;

import Classes.PermitHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class LinkedListQuickSort {

    //region singleton
    private static LinkedListQuickSort instance;


    public static LinkedListQuickSort getInstance() {
        if (instance == null) {
            instance = new LinkedListQuickSort();
        }
        return instance;
    }
    //endregion

    //region properties
    public enum sortBy {
        ID,
        FIRST_NAME,
        LAST_NAME,
        ADDRESS,
        CAR,
        START_DATE,
        END_DATE
    }

    public Boolean order = true;
    //endregion

    //region Methods

    // Sorts the passed in Linked List of PermitHolders using quicksort algorithm based on the given sortBy parameter
    public LinkedList<PermitHolder> quickSort(LinkedList<PermitHolder> list, int low, int high, sortBy sort) {
        int pivotIndex;
        if (low < high) {
            switch (sort) {
                case ID -> {
                    // Partition the list based on ID field and recursively sort the two partitions
                    pivotIndex = partitionID(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.ID);
                    quickSort(list, pivotIndex + 1, high, sortBy.ID);
                }
                case FIRST_NAME -> {
                    // Partition the list based on first name field and recursively sort the two partitions
                    pivotIndex = partitionFirstName(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.FIRST_NAME);
                    quickSort(list, pivotIndex + 1, high, sortBy.FIRST_NAME);
                }
                case LAST_NAME -> {
                    // Partition the list based on last name field and recursively sort the two partitions
                    pivotIndex = partitionLastName(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.LAST_NAME);
                    quickSort(list, pivotIndex + 1, high, sortBy.LAST_NAME);
                }
                case ADDRESS -> {
                    // Partition the list based on address field and recursively sort the two partitions
                    pivotIndex = partitionAddress(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.ADDRESS);
                    quickSort(list, pivotIndex + 1, high, sortBy.ADDRESS);
                }
                case CAR -> {
                    // Partition the list based on car field and recursively sort the two partitions
                    pivotIndex = partitionCar(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.CAR);
                    quickSort(list, pivotIndex + 1, high, sortBy.CAR);
                }
                case START_DATE -> {
                    // Partition the list based on start date field and recursively sort the two partitions
                    pivotIndex = partitionStartDate(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.START_DATE);
                    quickSort(list, pivotIndex + 1, high, sortBy.START_DATE);
                }
                case END_DATE -> {
                    // Partition the list based on end date field and recursively sort the two partitions
                    pivotIndex = partitionEndDate(list, low, high);
                    quickSort(list, low, pivotIndex - 1, sortBy.END_DATE);
                    quickSort(list, pivotIndex + 1, high, sortBy.END_DATE);
                }
            }
        }
        return list;
    }

    // Partitions the Linked List by the ID field using the QuickSort algorithm and Returns the index of the pivot element
    // The comments within this method apply to the rest of the methods for the First Name, Last Name, Address ect.
    private int partitionID(LinkedList<PermitHolder> list, int low, int high) {
        // Selects the last element in the partition as the pivot
        int pivot = list.get(high).getId();

        // Initializes the index of the smaller element to be one less than the lowest index
        int i = low - 1;

        // Loops through the partition, swapping elements as necessary to put them in the correct order
        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            int compare = permitHolder.getId() - pivot;

            // If order is ascending and the current element is less than the pivot OR
            // If order is descending and the current element is greater than the pivot
            if (order && compare < 0 || !order && compare > 0) {
                // Move the smaller element to the left of the partition
                i++;
                swap(list, i, j);
            }
        }

        // Swap the pivot element to its correct position in the partition
        swap(list, i + 1, high);

        // Return the index of the pivot element
        // If order is ascending, it is the element to the right of the smaller elements
        // If order is descending, it is the element to the left of the smaller elements
        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }


    private int partitionFirstName(LinkedList<PermitHolder> list, int low, int high) {
        String pivot = list.get(high).getFirst_name();

        // Initializes the index of the smaller element to be one less than the lowest index
        int i = low - 1;

        // Loops through the partition, swapping elements as necessary to put them in the correct order
        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            int compare = permitHolder.getFirst_name().compareTo(pivot);

            // If order is ascending and the current element is less than the pivot OR
            // If order is descending and the current element is greater than the pivot
            if (order && compare < 0 || !order && compare > 0) {
                // Move the smaller element to the left of the partition
                i++;
                swap(list, i, j);
            }
        }

        // Swap the pivot element to its correct position in the partition
        swap(list, i + 1, high);

        // Return the index of the pivot element
        // If order is ascending, it is the element to the right of the smaller elements
        // If order is descending, it is the element to the left of the smaller elements
        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionLastName(LinkedList<PermitHolder> list, int low, int high) {
        String pivot = list.get(high).getLast_name();
        // Initializes the index of the smaller element to be one less than the lowest index
        int i = low - 1;

        // Loops through the partition, swapping elements as necessary to put them in the correct order
        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            int compare = permitHolder.getLast_name().compareTo(pivot);

            // If order is ascending and the current element is less than the pivot OR
            // If order is descending and the current element is greater than the pivot
            if (order && compare < 0 || !order && compare > 0) {
                // Move the smaller element to the left of the partition
                i++;
                swap(list, i, j);
            }
        }

        // Swap the pivot element to its correct position in the partition
        swap(list, i + 1, high);

        // Return the index of the pivot element
        // If order is ascending, it is the element to the right of the smaller elements
        // If order is descending, it is the element to the left of the smaller elements
        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionAddress(LinkedList<PermitHolder> list, int low, int high) {
        String pivot = list.get(high).getAddress().toString();
        // Initializes the index of the smaller element to be one less than the lowest index
        int i = low - 1;

        // Loops through the partition, swapping elements as necessary to put them in the correct order
        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            int compare = permitHolder.getAddress().toString().compareTo(pivot);

            // If order is ascending and the current element is less than the pivot OR
            // If order is descending and the current element is greater than the pivot
            if (order && compare < 0 || !order && compare > 0) {
                // Move the smaller element to the left of the partition
                i++;
                swap(list, i, j);
            }
        }

        // Swap the pivot element to its correct position in the partition
        swap(list, i + 1, high);

        // Return the index of the pivot element
        // If order is ascending, it is the element to the right of the smaller elements
        // If order is descending, it is the element to the left of the smaller elements
        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }


    private int partitionCar(LinkedList<PermitHolder> list, int low, int high) {
        String pivot = list.get(high).getCar().toString();

        // Initializes the index of the smaller element to be one less than the lowest index
        int i = low - 1;

        // Loops through the partition, swapping elements as necessary to put them in the correct order
        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            int compare = permitHolder.getCar().toString().compareTo(pivot);

            // If order is ascending and the current element is less than the pivot OR
            // If order is descending and the current element is greater than the pivot
            if (order && compare < 0 || !order && compare > 0) {
                // Move the smaller element to the left of the partition
                i++;
                swap(list, i, j);
            }
        }

        // Swap the pivot element to its correct position in the partition
        swap(list, i + 1, high);

        // Return the index of the pivot element
        // If order is ascending, it is the element to the right of the smaller elements
        // If order is descending, it is the element to the left of the smaller elements
        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionStartDate(LinkedList<PermitHolder> list, int low, int high) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate pivot = LocalDate.parse(list.get(high).getPermit().sendStartDate(), formatter);

        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            LocalDate startDate = LocalDate.parse(permitHolder.getPermit().sendStartDate(), formatter);
            int compare = startDate.compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionEndDate(LinkedList<PermitHolder> list, int low, int high) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate pivot = LocalDate.parse(list.get(high).getPermit().sendExpiryDate(), formatter);

        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = list.get(j);
            LocalDate endDate = LocalDate.parse(permitHolder.getPermit().sendExpiryDate(), formatter);
            int compare = endDate.compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    // This method swaps two elements in the List by their indices.
    // The elements are of type PermitHolder and are accessed by their index in the list.
    // The method takes in the list to be sorted, and the indices of the two elements to be swapped.
    // It then retrieves the elements from the list, swaps their positions in the list, and puts them back in the list.
    private void swap(LinkedList<PermitHolder> list, int i, int j) {
        PermitHolder temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    //endregion
}