package com.springmvc.app.DAO;

import com.google.gson.Gson;
import com.springmvc.app.beans.JsonMessage;
import com.springmvc.app.beans.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDao {

    private static List<User> userList = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(UserDao.class);
    private static final Gson gson = new Gson();
    private static Map<String, String> loggerMap = new HashMap<>();
    private static final Marker SERVICE_MARKER = MarkerManager.getMarker("Service marker");

    private static int userCount = 3;

    static {
        userList.add(new User("1","Jack",new Date()));
        userList.add(new User("2","rock",new Date()));
        userList.add(new User("3","mack",new Date()));
    }

    public static List<User> getAllUsers() {
        logger.info("getAllUsers called ");
        //throw new RuntimeException("throwing runtime Exception");
        return userList;
    }

    public static User addUser(User user) {
        logger.info("UserDao addUser called");
        if(user.getId() == null) {
            user.setId(String.valueOf(++userCount));
        }
        user.setBirthDate(new Date());
        userList.add(user);
        return user;
    }

    public static User getUser(String id) {
        logger.info("Userdao getUser called ");
        for(User user:userList) {
            if(user.getId().equals(id)) {
                logger.info(gson.toJson(new JSONObject().put("content","user found")
                                .put("name", user.getName())
                                .put("id",user.getId())));
                loggerMap.put("name",user.getName());
                loggerMap.put("id",user.getId());
                loggerMap.put("content","user found");
                logger.info(loggerMap);
                loggerMap.clear();
                loggerMap.put("name","sdcsd");
                loggerMap.put("id","131");
                loggerMap.put("content","using logger map");
                logger.info(loggerMap);
                loggerMap.clear();
                logger.info(user);
                JsonMessage message = new JsonMessage();
                message.setContent("setting content");
                message.getMap().put("hi","fdd");
                message.getMap().put("dcd","sffv");
                logger.info(message);
                JsonMessage jsonMessage = new JsonMessage("content here",user);
                logger.info(SERVICE_MARKER,jsonMessage);
                return user;
            }
        }
        return null;
    }

    public static User deleteUser(String id) {
        logger.info("Userdao deleteUser called ");
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if(user.getId().equals(id)) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

}
