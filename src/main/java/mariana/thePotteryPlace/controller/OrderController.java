package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.dto.Response.ResponseOrderDTO;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController extends CrudController<Order, OrderDTO, ResponseOrderDTO,Long>{
    private final IOrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(IOrderService orderService, ModelMapper modelMapper) {
        super(Order.class, OrderDTO.class, ResponseOrderDTO.class);
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected ICrudService<Order, Long> getService() {
        return this.orderService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    @Override
    public ResponseEntity<ResponseOrderDTO> create(OrderDTO entity) {
        OrderDTO savedOrderDTO = orderService.saveCompleteOrder(entity);
        ResponseOrderDTO responseOrderDTO = modelMapper.map(savedOrderDTO, ResponseOrderDTO.class);

        return ResponseEntity.ok(responseOrderDTO);
    }

    @Override
    public ResponseEntity<List<ResponseOrderDTO>> findAll() {
        // Fetch all orders for the authenticated user (with their items)
        return orderService.findOrdersByUser();
    }


    @Override
    public ResponseEntity<Page<ResponseOrderDTO>> findAll(int page, int size, String order, Boolean asc) {
        if (order == null) {
            order = "date"; // sorts by date
        }
        if (asc == null) {
            asc = true; // Default to ascending
        }

        return orderService.findPageableOrdersByUser(page, size, order, asc);
    }

    @Override
    public ResponseEntity<ResponseOrderDTO> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
}
