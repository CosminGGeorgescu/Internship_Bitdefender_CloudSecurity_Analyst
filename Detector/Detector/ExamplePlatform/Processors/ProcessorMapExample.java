package Detector.ExamplePlatform.Processors;

import Detector.ExamplePlatform.Pojos.UserClassExample;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

public class ProcessorMapExample implements Function<JSONObject, Stream<UserClassExample>> {
    @Override
    public Stream<UserClassExample> apply(JSONObject jsonObject) {
        // Convert an array of users in JSON form to a stream of users in POJO form.
        // The resulting streams will be concatenated by java and the result will be returned
        // and processed further down the pipeline.

        JSONArray Users = jsonObject.getJSONArray("Users");
        ArrayList<UserClassExample> usersAsPojo = new ArrayList<>();

        for (Object currentUser : Users) {
            JSONObject userAsJson = (JSONObject) currentUser;
            Gson gson = new Gson();
            usersAsPojo.add(gson.fromJson(userAsJson.toString(), UserClassExample.class));
        }

        return usersAsPojo.stream();
    }

}
