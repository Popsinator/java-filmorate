package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    public void addFriend(User u1, User u2) {
        u1.getListIdFriends().add(u2.getId());
        u2.getListIdFriends().add(u1.getId());
    }

    public void deleteFriend(User u1, User u2) {
        u1.getListIdFriends().remove(u2.getId());
        u2.getListIdFriends().remove(u1.getId());
    }

    public List<Integer> outListJointFriends(User u1, User u2) {
        List<Integer> jointFriends= new ArrayList<>();
        for (Integer idFriend : u1.getListIdFriends()) {
            for (Integer friend : u2.getListIdFriends()) {
                if (Objects.equals(idFriend, friend)) {
                    jointFriends.add(idFriend);
                    break;
                }
            }
        }
        return jointFriends;
    }
}
