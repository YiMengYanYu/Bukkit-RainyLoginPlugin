import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YiMeng
 * @DateTime: 2024/4/13 20:46
 * @Description: TODO
 */
public class TestPlugin {

@Test
public void  test1(){
    List<String> arrayList = new ArrayList();
    arrayList.add("a");
    arrayList.add("a");
    arrayList.add("a");
    arrayList.add("a");
    arrayList.add("B");
    arrayList.add("A");
    arrayList.add("C");
    arrayList.add("D");
    arrayList=   arrayList.stream().distinct().collect(Collectors.toList());
    arrayList.forEach(
            System.out::print
    );
}


}
