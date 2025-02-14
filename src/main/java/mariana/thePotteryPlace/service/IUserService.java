package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.model.User;

import java.util.List;

public interface IUserService extends ICrudService<User, Long>{
    List<User> findUserByAuth();

}
