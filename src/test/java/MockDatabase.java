import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDatabase extends Database {
    private Map<String, JSONObject> mockRecipeDatabase = new HashMap<>();
    private Logger log = LoggerFactory.getLogger(MockDatabase.class);

    @Override
    public void insert(JSONObject recipeJSON) {
        String title = recipeJSON.getString("Title");
        mockRecipeDatabase.put(title, recipeJSON);
        //log.info("Inserted recipe with title: {}", title);
    }

    @Override
    public JSONObject get(String title) {
        if (mockRecipeDatabase.containsKey(title)) {
            //log.info("Retrieved recipe with title: {}", title);
            return mockRecipeDatabase.get(title);
        } else {
            //log.error("Title '{}' not found in mock database", title);
            return null;
        }
    }

    @Override
    public void updateIngredient(String title, String newIngredient) {
        if (mockRecipeDatabase.containsKey(title)) {
            JSONObject recipeJSON = mockRecipeDatabase.get(title);
            recipeJSON.put("Ingredients", newIngredient);
            //log.info("Updated ingredients for title: {}", title);
        } else {
           // log.warn("Recipe with title '{}' not found for ingredient update", title);
        }
    }

    @Override
    public void updateSteps(String title, List<String> newSteps) {
        if (mockRecipeDatabase.containsKey(title)) {
            JSONObject recipeJSON = mockRecipeDatabase.get(title);
            recipeJSON.put("Steps", newSteps);
            //log.info("Updated steps for title: {}", title);
        } else {
            //log.warn("Recipe with title '{}' not found for steps update", title);
        }
    }

    @Override
    public void delete(String title) {
        if (mockRecipeDatabase.remove(title) != null) {
            //log.info("Deleted recipe with title: {}", title);
        } else {
           // log.warn("Recipe with title '{}' not found for deletion", title);
        }
    }

    @Override
    public List<String> getAllTitles(String username) {
        List<String> recipes = new ArrayList<>();
        for (JSONObject recipeJSON : mockRecipeDatabase.values()) {
            if (recipeJSON.getString("User").equals(username)) {
                recipes.add(recipeJSON.getString("Title"));
            }
        }
        //log.info("Retrieved all titles for user: {}", username);
        return recipes;
    }

    @Override
    public void close() {
        //log.info("Mock database connection closed.");
    }
}
