package com.nguyen.security.serviceimpl;

import com.nguyen.security.UserService;
import com.nguyen.security.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private static List<User> userList;
    static {
        userList = new ArrayList<>();
        userList.add(new User(1,"Sam","Dalila","ADMIN"));
        userList.add(new User(2,"Kevin","Brain","ROOT"));
        userList.add(new User(3,"Nina","Condor","ROOT"));
        userList.add(new User(4,"Tito","Mend","GUEST"));
    }

    @Override
    public List<User> findAllUsers() {
        return userList;
    }

    @Override
    public User findById(Integer id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void updateUser(User user) {
        Integer index = findIndexByIdUser(user.getId(), userList);
        if (index != null) {
            userList.set(index.intValue(), user);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        Integer index = findIndexByIdUser(id, userList);
        if (index != null) {
            userList.remove(index.intValue());
        }
    }

    private static Integer findIndexByIdUser(Integer id, List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            if (id.equals(userList.get(i).getId())) {
                return i;
            }
        }
        return null;
    }
}
