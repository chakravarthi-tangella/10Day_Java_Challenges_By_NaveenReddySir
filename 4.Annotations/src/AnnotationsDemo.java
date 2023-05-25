import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface FootballPlayer
{
    int age() default 40;
    String country() default "portugal";
}

//@FootballPlayer(age = 40, country = "portugal")
@FootballPlayer
class Ronaldo
{
    int goals;
    int matches;

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }
}

public class AnnotationsDemo
{
    public static void main(String[] args) throws ClassNotFoundException {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.setGoals(2000);
        ronaldo.setMatches(950);

        System.out.println(ronaldo.getGoals());
        System.out.println(ronaldo.getMatches());

        System.out.println("Values from Annotations:");
        Class c = ronaldo.getClass();
        Annotation annotation = c.getAnnotation(FootballPlayer.class);
        FootballPlayer fp = (FootballPlayer) annotation;
        System.out.println("Age : " + fp.age());
        System.out.println("Country : " + fp.country());
    }
}
