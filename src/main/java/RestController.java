import io.javalin.*;
import static io.javalin.apibuilder.ApiBuilder.*;
import io.javalin.http.UploadedFile;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
                    String id = ctx.queryParam("id");
                    String title = ctx.queryParam("title");
                    if (title == null) {
                        log.error("Title is null");
                        throw new BadRequestResponse();
                    }
                    title = title.replaceAll("%20", " ");
                    String username = ctx.queryParam("user");
                    if (username == null || username.equals("")) {
                        log.error("Username is null");
                        throw new BadRequestResponse();
                    }
                    JSONObject responseBody = new JSONObject();
                    if (id != null) {
                        if (!id.equals("")) {
                            String recipeString = model.getDatabase().getByID(id).toString();
                            if (recipeString.equals("{}")) {
                                throw new NotFoundResponse();
                            }
                            ctx.json(recipeString);
                        }
                    }
                    if (title.equals("")) {
                        log.debug("Getting all titles");
                        responseBody.put("data", model.getDatabase().getAllTitles(username));
                        ctx.json(responseBody.toString());
                    }
                    else {
                        log.debug("Title queried: " + title);
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
                    post(ctx -> {
                        if (ctx.body().equals("")) {
                            log.error("Empty request body");
                            throw new BadRequestResponse();
                        }
                        JSONObject requestBody = new JSONObject(ctx.body());
                        String mealType = requestBody.getString("mealType");
                        String ingredients = requestBody.getString("ingredients");
                        log.info("Getting new recipe");
                        ctx.json(model.getNewRecipe(mealType, ingredients).toString());
                    });
                });

            });

            path("image", () -> {
                // Get new image (post)
                post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String title = requestBody.getString("title");
                    String url = model.getNewImage(title);
                    ctx.json(url);
                });

                // Update image url
                put(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String title = requestBody.getString("title");
                    String url = requestBody.getString("url");
                    model.getDatabase().updateImage(title, url);
                    ctx.json(url);
                });

                path("time", () -> {
                    // Update image timestamp
                    put(ctx -> {
                        if (ctx.body().equals("")) {
                            log.error("Empty request body");
                            throw new BadRequestResponse();
                        }
                        JSONObject requestBody = new JSONObject(ctx.body());
                        String title = requestBody.getString("title");
                        String timestamp = requestBody.getString("timestamp");
                        model.getDatabase().updateImageTime(title, timestamp);
                        ctx.json(timestamp);
                    });
                });
            });

            path("whisper", () -> {
                post(ctx -> {
                    UploadedFile file = ctx.uploadedFile("recording.wav");                     
                    String output = "Error";
                    if (file != null) {
                        File serverFile = new File(String.valueOf(Math.random()) + file.filename());
                        try (InputStream inputStream = file.content();
                            FileOutputStream outputStream = new FileOutputStream(serverFile)) {
                            inputStream.transferTo(outputStream);                            
                        } catch (IOException e) {
                            e.printStackTrace();
                            ctx.status(500).result("Error while writing file");
                        }
                        output = model.audioToText(serverFile);
                        serverFile.delete();
                        ctx.status(200).json(output);
                    } else {
                        ctx.status(400).result("No file found");
                    }
                });
            });

            path("auth", () -> {
                path("skip", () -> {
                    get(ctx -> {
                        String username = model.skipLogin();
                        String exist = "true";
                        if (username == null) {
                            username = "";
                            exist = "false";
                        }
                        JSONObject result = new JSONObject().put("username", username).put("exist", exist);
                        ctx.json(result.toString());
                    });
                });
                path("login", () -> {
                    post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String username = requestBody.getString("username");
                    String password = requestBody.getString("password");
                    String exist = String.valueOf(model.login(username, password));
                    ctx.json(exist);
                    });
                });
                path("session", () -> {
                    post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String username = requestBody.getString("username");
                    String exist = String.valueOf(model.sessionExist(username));
                    ctx.json(exist);
                    });
                });
                path("auto", () -> {
                    post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String username = requestBody.getString("username");
                    model.markAutoLogin(username);
                    });
                });
                path("check", () -> {
                    post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String username = requestBody.getString("username");
                    String exist = String.valueOf(model.checkUserExist(username));
                    ctx.json(exist);
                    });
                });
                path("create", () -> {
                    post(ctx -> {
                    if (ctx.body().equals("")) {
                        log.error("Empty request body");
                        throw new BadRequestResponse();
                    }
                    JSONObject requestBody = new JSONObject(ctx.body());
                    String username = requestBody.getString("username");
                    String confirmPassword = requestBody.getString("confirmPassword");
                    String firstName = requestBody.getString("firstName");
                    String lastName = requestBody.getString("lastName");
                    String phone = requestBody.getString("phone");
                    String exist = String.valueOf(model.createUser(username, confirmPassword, firstName, lastName, phone));
                    ctx.json(exist);
                    });
                });
            });

            path("share", () -> {
                get(ctx -> {
                    if (ctx.queryString() == null) {
                        log.error("Query string is null");
                        throw new BadRequestResponse();
                    }
                    String id = ctx.queryParam("id");
                    if (id == null || id.equals("")) {
                        log.error("ID is null");
                        throw new BadRequestResponse();
                    }
                    String recipeString = model.getDatabase().getByID(id).toString();
                    ctx.json(recipeString);
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

    // public static void getNewRecipe(Context ctx) {
    //     // Request body contains meal type and ingredients
    //     JSONObject requestBody = new JSONObject(ctx.body());
    //     String mealType = requestBody.getString("mealtype");
    //     String ingredients = requestBody.getString("ingredients");
    //     ctx.json(model.getNewRecipe(mealType, ingredients));

    // }
}