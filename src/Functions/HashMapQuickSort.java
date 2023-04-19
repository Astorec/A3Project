package Functions;

import Classes.PermitHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HashMapQuickSort {

    //region singleton
    private static HashMapQuickSort instance;


    public static HashMapQuickSort getInstance() {
        if (instance == null) {
            instance = new HashMapQuickSort();
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

    // Sorts the passed in Map of PermitHolders using quicksort algorithm based on the given sortBy parameter
    public Map<Integer, PermitHolder> quickSort(Map<Integer, PermitHolder> map, int low, int high, sortBy sort) {
        int pivotIndex;
        if (low < high) {
            switch (sort) {
                case ID -> {
                    // Partition the map based on ID field and recursively sort the two partitions
                    pivotIndex = partitionID(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.ID);
                    quickSort(map, pivotIndex + 1, high, sortBy.ID);
                }
                case FIRST_NAME -> {
                    // Partition the map based on first name field and recursively sort the two partitions
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionFirstName(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.FIRST_NAME);
                    quickSort(map, pivotIndex + 1, high, sortBy.FIRST_NAME);
                }
                case LAST_NAME -> {
                    // Partition the map based on last name field and recursively sort the two partitions
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionLastName(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.LAST_NAME);
                    quickSort(map, pivotIndex + 1, high, sortBy.LAST_NAME);
                }
                case ADDRESS -> {
                    // Partition the map based on address field and recursively sort the two partitions
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionAddress(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.ADDRESS);
                    quickSort(map, pivotIndex + 1, high, sortBy.ADDRESS);
                }
                case CAR -> {
                    // Partition the map based on car field and recursively sort the two partitions
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionCar(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.CAR);
                    quickSort(map, pivotIndex + 1, high, sortBy.CAR);
                }
                case START_DATE -> {
                    // Partition the map based on start date field and recursively sort the two partitions
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionStartDate(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.START_DATE);
                    quickSort(map, pivotIndex + 1, high, sortBy.START_DATE);
                }
                case END_DATE -> {
                    // Partition the map based on end date field and recursively sort the two partitions
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionEndDate(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.END_DATE);
                    quickSort(map, pivotIndex + 1, high, sortBy.END_DATE);
                }
            }
        }
        return map;
    }

    // Partitions the map by the ID field using the QuickSort algorithm and Returns the index of the pivot element
    // The comments within this method apply to the rest of the methods for the First Name, Last Name, Address ect.
    private int partitionID(Map<Integer, PermitHolder> map, int low, int high) {
        // Selects the last element in the partition as the pivot
        int pivot = map.get(high).getId();

        // Initializes the index of the smaller element to be one less than the lowest index
        int i = low - 1;

        // Loops through the partition, swapping elements as necessary to put them in the correct order
        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getId() - pivot;

            // If order is ascending and the current element is less than the pivot OR
            // If order is descending and the current element is greater than the pivot
            if (order && compare < 0 || !order && compare > 0) {
                // Move the smaller element to the left of the partition
                i++;
                swap(map, i, j);
            }
        }

        // Swap the pivot element to its correct position in the partition
        swap(map, i + 1, high);

        // Return the index of the pivot element
        // If order is ascending, it is the element to the right of the smaller elements
        // If order is descending, it is the element to the left of the smaller elements
        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionFirstName(Map<Integer, PermitHolder> map, int low, int high) {
        String pivot = map.get(high).getFirst_name();

        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getFirst_name().compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(map, i, j);
            }
        }

        swap(map, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionLastName(Map<Integer, PermitHolder> map, int low, int high) {
        String pivot = map.get(high).getLast_name();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getLast_name().compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(map, i, j);
            }
        }

        swap(map, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionAddress(Map<Integer, PermitHolder> map, int low, int high) {
        String pivot = map.get(high).getAddress().toString();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getAddress().toString().compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(map, i, j);
            }
        }

        swap(map, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionCar(Map<Integer, PermitHolder> map, int low, int high) {
        String pivot = map.get(high).getCar().toString();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getCar().toString().compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(map, i, j);
            }
        }

        swap(map, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionStartDate(Map<Integer, PermitHolder> map, int low, int high) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate pivot = LocalDate.parse(map.get(high).getPermit().sendStartDate(), formatter);

        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            LocalDate startDate = LocalDate.parse(permitHolder.getPermit().sendStartDate(), formatter);
            int compare = startDate.compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(map, i, j);
            }
        }

        swap(map, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    private int partitionEndDate(Map<Integer, PermitHolder> map, int low, int high) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate pivot = LocalDate.parse(map.get(high).getPermit().sendExpiryDate(), formatter);

        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            LocalDate endDate = LocalDate.parse(permitHolder.getPermit().sendExpiryDate(), formatter);
            int compare = endDate.compareTo(pivot);

            if (order && compare < 0 || !order && compare > 0) {
                i++;
                swap(map, i, j);
            }
        }

        swap(map, i + 1, high);

        if (order) {
            return i + 1;
        } else {
            return high - (i + 1) + low;
        }
    }

    // This method swaps two elements in the map by their indices.
    // The elements are of type PermitHolder and are accessed by their index in the map.
    // The method takes in the map to be sorted, and the indices of the two elements to be swapped.
    // It then retrieves the elements from the map, swaps their positions in the map, and puts them back in the map.
    private void swap(Map<Integer, PermitHolder> map, int i, int j) {
        PermitHolder temp = map.get(i);
        map.put(i, map.get(j));
        map.put(j, temp);
    }
    //endregion
}