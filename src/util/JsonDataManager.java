
package util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonDataManager {

    private static final String USERS_FILE_PATH = "database/users.json";

    // Method to read users data from JSON file
    public static List<User> readUsersDataFromFile() {
        try (FileReader reader = new FileReader(USERS_FILE_PATH)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to write updated users data to JSON file
    public static void writeUsersDataToFile(List<User> userList) {
        try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
