package data_access.repository;

import entity.CommonMatches;
import entity.User;

public interface CustomUserRepository {

    void saveUser(User user);
    void changePassword(User user);
    User get(String username);
}
