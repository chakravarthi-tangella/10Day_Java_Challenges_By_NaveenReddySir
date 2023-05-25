import java.lang.reflect.Method;

class Apple{
    private void repair(int cost)
    {
        System.out.println("Repairing cost is : " + cost);
    }
}
public class ReflectionAPIdemo2 {
    public static void main(String[] args) throws Exception{
        Class c = Class.forName("Apple");
        Apple apple = (Apple) c.newInstance();
        Method method = c.getDeclaredMethod("repair",int.class);
        method.setAccessible(true);
        method.invoke(apple, 40);
    }
}
