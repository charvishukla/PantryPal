// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.json.JSONObject;

// import java.time.Instant;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// class ControllerTest{
//   MockController controller = new MockController();

//     @Test
//     void sortRecipeCardsByTitle_SortedAlphabetically() {
//         List<RecipeCard> cards = Arrays.asList(
//             new RecipeCard("Banana Bread", "Dessert", Instant.now().toString()),
//             new RecipeCard("Apple Pie", "Dessert", Instant.now().toString()),
//             new RecipeCard("Chocolate Cake", "Dessert", Instant.now().toString())
//         );

//         controller.sortRecipeCardsByTitle(cards);

//         assertEquals("Apple Pie", cards.get(0).getTitle());
//         assertEquals("Banana Bread", cards.get(1).getTitle());
//         assertEquals("Chocolate Cake", cards.get(2).getTitle());
//     }

//     @Test
//     void sortRecipeCardsByTitle_CaseInsensitiveSorting() {
//         List<RecipeCard> cards = Arrays.asList(
//             new RecipeCard("banana Bread", "Dessert", Instant.now().toString()),
//             new RecipeCard("apple Pie", "Dessert", Instant.now().toString()),
//             new RecipeCard("chocolate Cake", "Dessert", Instant.now().toString())
//         );

//         controller.sortRecipeCardsByTitle(cards);

//         assertEquals("apple Pie", cards.get(0).getTitle());
//         assertEquals("banana Bread", cards.get(1).getTitle());
//         assertEquals("chocolate Cake", cards.get(2).getTitle());
//     }

//     @Test
//     void sortRecipeCardsByMealType_SortedByMealType() {
//         List<RecipeCard> cards = Arrays.asList(
//             new RecipeCard("Banana Bread", "Snack", Instant.now().toString()),
//             new RecipeCard("Apple Pie", "Dessert", Instant.now().toString()),
//             new RecipeCard("Chocolate Cake", "Breakfast", Instant.now().toString())
//         );

//         controller.sortRecipeCardsByMealType(cards);

//         assertEquals("Apple Pie", cards.get(0).getTitle());
//         assertEquals("Chocolate Cake", cards.get(1).getTitle());
//         assertEquals("Banana Bread", cards.get(2).getTitle());
//     }

//     @Test
//     void sortRecipeCardsByMealType_SameMealType() {
//         List<RecipeCard> cards = Arrays.asList(
//             new RecipeCard("Banana Bread", "Dessert", Instant.now().toString()),
//             new RecipeCard("Apple Pie", "Dessert", Instant.now().toString()),
//             new RecipeCard("Chocolate Cake", "Dessert", Instant.now().toString())
//         );

//         controller.sortRecipeCardsByMealType(cards);

//         // Assuming the original order is preserved as all have the same meal type
//         assertEquals("Banana Bread", cards.get(0).getTitle());
//         assertEquals("Apple Pie", cards.get(1).getTitle());
//         assertEquals("Chocolate Cake", cards.get(2).getTitle());
//     }

//     @Test
//     void sortRecipeCardsByTime_SortedByTime() {
//         List<RecipeCard> cards = Arrays.asList(
//             new RecipeCard("Banana Bread", "Dessert", Instant.now().minusSeconds(300).toString()),
//             new RecipeCard("Apple Pie", "Dessert", Instant.now().minusSeconds(600).toString()),
//             new RecipeCard("Chocolate Cake", "Dessert", Instant.now().toString())
//         );

//         controller.sortRecipeCardsByTime(cards);

//         assertEquals("Apple Pie", cards.get(0).getTitle());
//         assertEquals("Banana Bread", cards.get(1).getTitle());
//         assertEquals("Chocolate Cake", cards.get(2).getTitle());
//     }

//     @Test
//     void sortRecipeCardsByTime_ReverseChronologicalOrder() {
//         List<RecipeCard> cards = Arrays.asList(
//             new RecipeCard("Banana Bread", "Dessert", Instant.now().minusSeconds(300).toString()),
//             new RecipeCard("Apple Pie", "Dessert", Instant.now().minusSeconds(100).toString()),
//             new RecipeCard("Chocolate Cake", "Dessert", Instant.now().minusSeconds(200).toString())
//         );

//         controller.sortRecipeCardsByTime(cards);

//         assertEquals("Banana Bread", cards.get(0).getTitle());
//         assertEquals("Chocolate Cake", cards.get(1).getTitle());
//         assertEquals("Apple Pie", cards.get(2).getTitle());
//     }


// }
