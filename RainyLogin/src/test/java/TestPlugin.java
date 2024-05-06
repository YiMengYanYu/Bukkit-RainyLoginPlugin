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

    String testStr1 = "1_3";
    String testStr2 = "32S1"; // 包含空格，应返回false
    String testStr3 = "4_S";



}

    public static boolean matches(String input) {
        String regex = "^[a-zA-Z0-9_]*$";
        return input.matches(regex);
    }

}
