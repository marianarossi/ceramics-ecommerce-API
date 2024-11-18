package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.service.IListService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ListController <T, D, R, ID extends Serializable> {
    protected abstract IListService<T, ID> getService();
    protected abstract ModelMapper getModelMapper();

    private final Class<T> typeClass;
    private final Class<D> typeDtoClass;
    private final Class<R> typeResponseDtoClass;

    public ListController(Class<T> typeClass, Class<D> typeDtoClass, Class<R> typeResponseDtoClass) {
        this.typeClass = typeClass;
        this.typeDtoClass = typeDtoClass;
        this.typeResponseDtoClass = typeResponseDtoClass;
    }

    private D convertToDto(T entity) {
        return getModelMapper().map(entity, this.typeDtoClass);
    }

    private R convertToResponseDto(T entity) {
        return getModelMapper().map(entity, this.typeResponseDtoClass);
    }

    private T convertToEntity(D entityDto) {
        return getModelMapper().map(entityDto, this.typeClass);
    }

    @GetMapping //http://ip.api:port/classname
    public ResponseEntity<List<R>> findAll() {
        return ResponseEntity.ok(
                getService().findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList())
        );
    }

    @GetMapping("page")  //http://ip.api:port/classname/page
    public ResponseEntity<Page<R>> findAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Boolean asc
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return ResponseEntity.ok(
                getService().findAll(pageRequest).map(this::convertToResponseDto)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<R> findOne(@PathVariable ID id) {
        T entity = getService().findOne(id);
        if (entity != null) {
            return ResponseEntity.ok(convertToResponseDto(entity));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable ID id) {
        return ResponseEntity.ok(getService().exists(id));
    }

    @GetMapping("count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(getService().count());
    }
}
