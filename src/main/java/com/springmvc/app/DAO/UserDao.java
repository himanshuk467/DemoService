package com.springmvc.app.DAO;

import com.google.gson.Gson;
import com.springmvc.app.beans.JsonMessage;
import com.springmvc.app.beans.LogMap;
import com.springmvc.app.beans.LogMapKeys;
import com.springmvc.app.beans.User;
import org.apache.logging.log4j.*;
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
        //throw new RuntimeException("thrown");
        logger.info("Userdao getUser called ");
        for(User user:userList) {
            if(user.getId().equals(id)) {
                loggerMap.clear();
                loggerMap.put("name","sdcsd");
                loggerMap.put("id","131");
                loggerMap.put("content","using logger map");
                logger.info(loggerMap);
                loggerMap.clear();
                logger.info(user);
                JsonMessage message = new JsonMessage();
                message.setContent("setting content");
                //message.getMap().put("hi","fdd");
                //message.getMap().put("dcd","sffv");
                logger.info(message);

                JsonMessage jsonMessage1 = new JsonMessage();
                jsonMessage1.setContent("content2");
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                jsonMessage1.setMap(map);
                Long timetaken = 5L;
                String time = timetaken + "ms";
                ThreadContext.put("time_taken",time);
                logger.info(jsonMessage1);
                ThreadContext.remove("time_taken");

                JsonMessage jsonMessage = new JsonMessage();
                jsonMessage.setContent("content here");
                map.clear();
                map.put("userrrrr", user);
                map.put("istrue",Boolean.TRUE);
                List<String> list = new ArrayList<>();
                list.add("sdcd");
                list.add("wedwed");
                Map<String, User> map1 = new HashMap<>();
                map1.put("user mao",user);
                map.put("mapp", map1);
                map.put("list", list);
                jsonMessage.setMap(map);
                logger.info(SERVICE_MARKER,jsonMessage);

                LogMap logMap = new LogMap.Builder()
                        .withKey(LogMapKeys.KEY1.name(), "value11")
                        .withKey(LogMapKeys.KEY2.name(), "value12")
                        .build();

                logger.info(logMap.getmap());

                logMap = new LogMap.Builder()
                        .withKey(LogMapKeys.KEY1.name(), "value11")
                        .build();

                logger.info(logMap.getmap());

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
