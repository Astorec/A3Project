package Functions;

import Classes.PermitHolder;

import java.util.Map;

public class HashMapQuickSort {

    private static HashMapQuickSort instance;


    public static HashMapQuickSort getInstance() {
        if (instance == null) {
            instance = new HashMapQuickSort();
        }
        return instance;
    }

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

    public Map<Integer, PermitHolder> quickSort(Map<Integer, PermitHolder> map, int low, int high, sortBy sort) {
        int pivotIndex;
        if (low < high) {
            switch (sort) {
                case ID:
                    pivotIndex = partitionID(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.ID);
                    quickSort(map, pivotIndex + 1, high, sortBy.ID);
                    break;
                case FIRST_NAME:
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionFirstName(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.FIRST_NAME);
                    quickSort(map, pivotIndex + 1, high, sortBy.FIRST_NAME);
                    break;
                    case LAST_NAME:
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionLastName(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.LAST_NAME);
                    quickSort(map, pivotIndex + 1, high, sortBy.LAST_NAME);
                    break;
                case ADDRESS:
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionAddress(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.ADDRESS);
                    quickSort(map, pivotIndex + 1, high, sortBy.ADDRESS);
                    break;
                case CAR:
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionCar(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.CAR);
                    quickSort(map, pivotIndex + 1, high, sortBy.CAR);
                    break;
                case START_DATE:
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionStartDate(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.START_DATE);
                    quickSort(map, pivotIndex + 1, high, sortBy.START_DATE);
                    break;
                case END_DATE:
                    System.out.println("Size of permitHolderMap: " + map.size());
                    pivotIndex = partitionEndDate(map, low, high);
                    quickSort(map, low, pivotIndex - 1, sortBy.END_DATE);
                    quickSort(map, pivotIndex + 1, high, sortBy.END_DATE);
                    break;
            }
        }
        System.out.println("Size of permitHolderMap: " + map.size());
        return map;
    }

    private int partitionID(Map<Integer, PermitHolder> map, int low, int high) {
        int pivot = map.get(high).getId();

        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getId() - pivot;

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
        String pivot = map.get(high).getPermit().sendStartDate();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getPermit().sendStartDate().compareTo(pivot);

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
        String pivot = map.get(high).getPermit().sendExpiryDate();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            PermitHolder permitHolder = map.get(j);
            int compare = permitHolder.getPermit().sendExpiryDate().compareTo(pivot);

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




    private void swap(Map<Integer, PermitHolder> map, int i, int j) {
        PermitHolder temp = map.get(i);
        map.put(i, map.get(j));
        map.put(j, temp);
    }
}