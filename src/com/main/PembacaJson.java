package com.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PembacaJson {
    public List<User> readUsersFromJson(String filePath) {
        Gson gson = new Gson();
        List<User> users = null;

        try (FileReader reader = new FileReader(filePath)) {
            Type userListType = new TypeToken<List<User>>(){}.getType();
            users = gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
