// import java.util.Comparator;
// import java.util.List;

// import javafx.event.ActionEvent;

// import java.time.LocalDateTime;


// class MockController {
//     public void handleFilterBoxClick(ActionEvent event) {
//         String filter = "all"; // Mock filter value
//         RecipeList recipeList = new RecipeList(); // Mock RecipeList
//         List<RecipeCard> recipeCards = recipeList.getRecipeCards(); // Get mock recipe cards
//         recipeList.deleteAllOnScene();
    
//         for (RecipeCard recipeCard : recipeCards) {
//             if (filter.equals("all") || recipeCard.getMealType().equals(filter)) {
//                 recipeList.addRecipeCardOnScene(recipeCard);
//             }
//         }
//     }


//     public void handleSortingBoxClick(ActionEvent event) {
//         String sorting = "Alphabetically"; // Mock sorting value
//         RecipeList recipeList = new RecipeList(); // Mock RecipeList
//         List<RecipeCard> recipeCards = recipeList.getRecipeCards(); // Get mock recipe cards
    
//         // Sorting logic
//         recipeCards.sort((card1, card2) -> {
//             switch (sorting) {
//                 case "Alphabetically":
//                     return card1.getTitle().compareToIgnoreCase(card2.getTitle());
//                 case "Newest to Oldest":
//                     return card2.getTime().compareTo(card1.getTime());
//                 default: // Oldest to Newest
//                     return card1.getTime().compareTo(card2.getTime());
//             }
//         });
    
//         recipeList.deleteAllOnScene();
//         recipeCards.forEach(recipeList::addRecipeCardOnScene);
//     }
    
// }