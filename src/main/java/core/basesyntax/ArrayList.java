package core.basesyntax;

//import jdk.internal.util.ArraysSupport;
import java.util.Arrays;
import java.util.NoSuchElementException;


public class ArrayList<T> implements List<T> {


    private static final int def_capacity = 10;
    private static final Object[] empty_arraydata = {};


    private T[] array = (T[]) new Object[def_capacity];
    private int size;
    private int cursor;


    private String errorMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }


    private Object[] grow() {
        int oldCapacity = array.length;
        double multiplicator = 1;
        if (oldCapacity == size) {
            multiplicator = 1.5;
        }
        if (oldCapacity > 0 || array != empty_arraydata) {
            int newCapacity = (int) (oldCapacity * multiplicator + 1);
            return array = Arrays.copyOf(array, newCapacity);
        } else {
            return array = (T[]) new Object[def_capacity];
        }
    }


    public static Object[] listToArray(List<?> list) {
        if (!list.isEmpty()) {
            Object[] tempArray = new Object[list.size()];
            for (int index = 0; index < list.size(); index++) {
                tempArray[index] = list.get(index);
            }
            return tempArray;
        }
        return null;
    }


    @Override
    public void add(T value) {
        int index = cursor;
        ArrayList.this.add(value, index);
        cursor = index + 1;
    }


    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(errorMsg(index));
        } else {
            int s = size;
            if (s == array.length) {
                array = (T[]) grow();
            }
            System.arraycopy(array, index,
                array, index + 1,
                s - index);
            array[index] = value;
            size = s + 1;
        }
    }


    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            T[] tempArray = (T[]) listToArray(list);
            int newCapacity = size + tempArray.length;
            array = Arrays.copyOf(array, newCapacity);
            System.arraycopy(tempArray, 0, array, size, tempArray.length);
            size = newCapacity;
        }
    }


    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(errorMsg(index));
        } else {
            return array[index];
        }
    }


    @Override
    public void set(T value, int index) {
        if (index >= size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(errorMsg(index));
        } else {
            array[index] = value;
        }
    }


    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(errorMsg(index));
        } else {
            int newSize = size - 1;
            final T oldValue = get(index);
            if (newSize > index) {
                System.arraycopy(array, index + 1, array, index, newSize - index);
            }
            size = newSize;
            array[newSize] = null;
            return oldValue;
        }
    }


    @Override
    public T remove(T element) {
        int index = 0;
        found:
        {
            if (element == null) {
                for (; index < size; index++) {
                    if (array[index] == null) {
                        remove(index);
                        break found;
                    }
                }
            } else {
                for (; index < size; index++) {
                    if (element.equals(array[index])) {
                        remove(index);
                        break found;
                    }
                }
                throw new NoSuchElementException();
            }
        }
        return element;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        boolean empty = true;
        for (T element : array) {
            if (element != null) {
                empty = false;
                break;
            }
        }
        return empty;
    }
}
