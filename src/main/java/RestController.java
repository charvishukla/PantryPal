import io.javalin.*;
import static io.javalin.apibuilder.ApiBuilder.*;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestController {
    Model model;

    public RestController(Model model) {
        this.model = model;
        start();
    }

    public static void start() {
        Javalin app = Javalin.create().start(7000);
        Logger log = LoggerFactory.getLogger(RestController.class);
        Model model = new Model();
        app.routes(() -> {
            path("recipe", () -> {

                get(ctx -> {
                    System.out.println("Working");
                    ctx.json(model.getDatabase().getAllTitles());
                });

                path("generate", () -> {
                    get(ctx -> {
                        if (ctx.body().equals("")) {
                            throw new BadRequestResponse();
                        }
                        JSONObject requestBody = new JSONObject(ctx.body());
                        String mealType = requestBody.getString("mealtype");
                        String ingredients = requestBody.getString("ingredients");
                        log.info("Getting new recipe");
                        ctx.json(model.getNewRecipe(mealType, ingredients));
                    });
                });

            });
        });

        app.exception(Exception.class, (e, ctx) -> {
            log.error(e.toString());
            // JSONObject responseBody = new JSONObject();
            // responseBody.put("error", "Bad Request");
            // ctx.status(401).json(responseBody.toString());
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            app.stop();
        }));

        app.events(event -> {
            event.serverStopped(() -> {
                log.info("Stopped PantryPal Server");
            });
        });
    }

    public static void postStuff(Context ctx) {
        System.out.println("psted");
    }

    // public static void getNewRecipe(Context ctx) {
    //     // Request body contains meal type and ingredients
    //     JSONObject requestBody = new JSONObject(ctx.body());
    //     String mealType = requestBody.getString("mealtype");
    //     String ingredients = requestBody.getString("ingredients");
    //     ctx.json(model.getNewRecipe(mealType, ingredients));

    // }
}