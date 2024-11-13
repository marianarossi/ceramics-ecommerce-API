package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.service.IListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class ListServiceImpl <T, ID extends Serializable> implements IListService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public T findOne(ID id) {
        return getRepository().findById(id).orElse(null);
    }

}
