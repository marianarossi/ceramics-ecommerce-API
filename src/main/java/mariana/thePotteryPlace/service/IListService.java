package mariana.thePotteryPlace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface IListService<T, ID extends Serializable> {
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    T findOne(ID id);
    boolean exists(ID id);
    long count();
}
