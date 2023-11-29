import java.util.List;


public class Helper {
    public static String _splitTitle(List<String> s){
        String title = s.get(0) + "\n";
        return title;
    }

    public static String[] _splitIngred(List<String> s) {
        String tidyIngred = s.get(1).replaceAll("\n+", "\n");
        String[] ingredList = tidyIngred.split("\n");
        return ingredList;
    }

    public static String _splitMealType(List<String> s) {
        String mealType = s.get(s.size()-1);
        return mealType;
    }

}
