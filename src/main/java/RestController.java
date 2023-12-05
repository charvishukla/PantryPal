import io.javalin.*;
import static io.javalin.apibuilder.ApiBuilder.*;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;

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

                post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    model.getDatabase().insert(requestBody);
                });
                
                // Get Recipe Title(s)
                get(ctx -> {
                    if (ctx.queryString() == null) {
                        log.error("Query string is null");
                        throw new BadRequestResponse();
                    }
                    String title = ctx.queryParam("title");
                    if (title == null) {
                        log.error("Title is null");
                        throw new BadRequestResponse();
                    }
                    String username = ctx.queryParam("user");
                    if (username == null || username.equals("")) {
                        log.error("Username is null");
                        throw new BadRequestResponse();
                    }
                    JSONObject responseBody = new JSONObject();
                    if (title.equals("")) {
                        log.debug("Getting all titles");
                        responseBody.put("data", model.getDatabase().getAllTitles(username));
                        ctx.json(responseBody.toString());
                    }
                    else {
                        log.info("Title queried: " + title);
                        String recipeString = model.getDatabase().get(title).toString();
                        if (recipeString.equals("{}")) {
                            throw new NotFoundResponse();
                        }
                        ctx.json(recipeString);
                    }
                });

                // Edit recipe
                put(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String title = requestBody.getString("title");
                    JSONArray steps = requestBody.getJSONArray("steps");
                    // Possibly add something to check type of put, then call appropriate update
                    List<String> listSteps = new ArrayList<String>();
                    for (int i = 0; i < steps.length(); i++) {
                        listSteps.add(steps.getString(i));
                    }
                    System.out.println(steps.get(0));
                    model.getDatabase().updateSteps(title, listSteps);
                    ctx.json(model.getDatabase().get(title).toString());
                });

                // Delete recipe
                delete(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String title = requestBody.getString("title");
                    model.getDatabase().delete(title);
                });

                path("generate", () -> {
                    get(ctx -> {
                        if (ctx.body().equals("")) {
                            log.error("Empty request body");
                            throw new BadRequestResponse();
                        }
                        JSONObject requestBody = new JSONObject(ctx.body());
                        String mealType = requestBody.getString("mealtype");
                        String ingredients = requestBody.getString("ingredients");
                        log.info("Getting new recipe");
                        ctx.json(model.getNewRecipe(mealType, ingredients).toString());
                    });
                });

            });
        });

        app.ws("ws/audio", ws -> {
            ws.onConnect(ctx -> log.info("Client connected to WebSocket"));
            ws.onMessage(ctx -> {
                File file = ctx.messageAsClass(File.class);
                ctx.send(model.audioToText(file));
            });
            ws.onClose(ctx -> log.info("Connection to WebSocket closed"));
        });

        app.exception(Exception.class, (e, ctx) -> {
            log.error(e.toString());
            e.printStackTrace();
            // JSONObject responseBody = new JSONObject();
            // responseBody.put("error", "Bad Request");
            // ctx.status(401).json(responseBody.toString());
            throw new HttpResponseException(400);
        });

        app.exception(JSONException.class, (e, ctx) -> {
            log.error(e.toString());
            e.printStackTrace();
            throw new BadRequestResponse();
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