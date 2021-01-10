package src.tool;

import java.util.List;

/**
 *
 */
public class ListManipulator {

    /**
     *
     */
    private ListManipulator(){}

    /**
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> long numberOfElements(List<T> list){
        long numberOfElements = 0L;
        for(var element : list){
            if (element != null){
                numberOfElements++;
            }
        }
        return numberOfElements;
    }
}
