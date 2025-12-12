import java.lang.reflect.Field;

public class StringDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        /**
         * need vm options: --add-opens java.base/java.lang=ALL-UNNAMED
         */
        String str = "hello world";
        Field value = str.getClass().getDeclaredField("value");
        value.setAccessible(true);
        byte[] bytes = (byte[]) value.get(str);
        bytes[0] = 'A';
        System.out.println(str);
    }
}
