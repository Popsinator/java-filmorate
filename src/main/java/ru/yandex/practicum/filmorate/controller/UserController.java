package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j//Создаю логгер
@Data
@RestController
public class UserController {

    InMemoryUserStorage userStorage = new InMemoryUserStorage(new UserService());

    @GetMapping("/users")//Получение списка всех пользователей
    public Collection<User> findAll() {
        log.trace("Текущее количество пользователей: {}", userStorage.getUsers().size());
        return userStorage.findAllUser();
    }

    @PostMapping("/users")//Добавление нового пользователя
    public User create(@RequestBody User user) {
        return userStorage.createUser(user);
    }

    @PutMapping("/users")//Обновление пользователя
    public User put(@RequestBody User user) {
        return userStorage.updateUser(user);
    }

    @GetMapping("/users/{userId}")
    public User findPost(@PathVariable("userId") Integer userId) {
        return userStorage.findUserById(userId);
    }

    @PutMapping("/users/{id}/friends/{friendId}")//
    public User put(@PathVariable("id") Integer id,
                    @PathVariable("friendId") Integer friendId) {
        userStorage.getUserService().addFriend(userStorage.findUserById(id), userStorage.findUserById(friendId));
        return userStorage.findUserById(id);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")//Обновление пользователя
    public User delete(@PathVariable("id") Integer id,
                       @PathVariable("friendId") Integer friendId) {
        userStorage.getUserService().deleteFriend(userStorage.findUserById(id), userStorage.findUserById(friendId));
        return userStorage.findUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findListFriendUser(@PathVariable("id") Integer id) {
        List<User> listFriend = new ArrayList<>();
        for (Integer idFriend : userStorage.findUserById(id).getListIdFriends()) {
            listFriend.add(userStorage.findUserById(idFriend));
        }
        return listFriend;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> findListCommonFriendUser(@PathVariable("id") Integer id,
                                                  @PathVariable("otherId") Integer otherId) {
        List<User> usersCommon = new ArrayList<>();
        for (Integer idCommon : userStorage.getUserService().outListJointFriends
                (userStorage.findUserById(id), userStorage.findUserById(otherId))) {
            usersCommon.add(userStorage.findUserById(idCommon));
        }
        return usersCommon;
    }
}
