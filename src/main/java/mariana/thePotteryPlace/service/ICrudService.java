package mariana.thePotteryPlace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface ICrudService<T, ID extends Serializable> {
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    T findOne(ID id);
    T save(T entity);
    boolean exists(ID id);
    long count();
    void delete(ID id);
//    List<T> findAll(Sort sort);
//    T saveAndFlush(T entity);
//    Iterable<T> save(Iterable<T> iterable);
//    void flush();
//    void delete(Iterable<? extends T> iterable);
//    void deleteAll();


}
