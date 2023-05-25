import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionAPIdemo {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c = Class.forName("Product");

        System.out.println(c.getConstructors().length);

        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor :
                constructors) {
            System.out.println(constructor);
        }

        System.out.println("==================================");

        Field[] fields =c.getDeclaredFields();
        for (Field field :
                fields) {
            System.out.println(field);
        }

        System.out.println("==================================");

        Method[] methods =c.getMethods();
        for (Method method :
                methods) {
            System.out.println(method);
        }

        System.out.println("==================================");


    }
}
