package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.service.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
// T = class type, D = dto type, ID = attribute related to primary key type
public abstract class CrudController <T, D, R, ID extends Serializable> {

    protected abstract ICrudService<T, ID> getService();
    protected abstract ModelMapper getModelMapper();

    private final Class<T> typeClass;
    private final Class<D> typeDtoClass;
    private final Class<R> typeResponseDtoClass;

    public CrudController(Class<T> typeClass, Class<D> typeDtoClass, Class<R> typeResponseDtoClass) {
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
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<R> create(@RequestBody @Valid D entityDto) {
        T entity = convertToEntity(entityDto);
        T savedEntity = getService().save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDto(savedEntity));
    }

    @PutMapping("{id}")
    public ResponseEntity<R> update(@PathVariable ID id, @RequestBody @Valid D dto) {
        T entity = getService().findOne(id);

        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        getModelMapper().map(dto, entity);

        T updatedEntity = getService().save(entity);

        R responseDTO = getModelMapper().map(updatedEntity, (Class<R>) dto.getClass());

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable ID id) {
        return ResponseEntity.ok(getService().exists(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(getService().count());
    }
}

