import org.junit.Test;

import java.util.Optional;

/**
 * @author YiMeng
 * @DateTime: 2024/4/13 20:46
 * @Description: TODO
 */
public class TestPlugin {

@Test
public void  test1(){

    Optional<String> o = Optional.ofNullable(null);

    System.out.println(o.get());


}


}
