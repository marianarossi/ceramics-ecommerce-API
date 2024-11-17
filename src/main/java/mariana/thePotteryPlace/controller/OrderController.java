package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.dto.Response.ResponseOrderDTO;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
        orderService.saveCompleteOrder(entity);
        return ResponseEntity.ok(modelMapper.map(entity, ResponseOrderDTO.class));
    }

    @Override
    public ResponseEntity<List<ResponseOrderDTO>> findAll() {
        List<Order> userOrders = orderService.findOrdersByUser();

        List<ResponseOrderDTO> responseOrders = userOrders.stream()
                .map(order -> modelMapper.map(order, ResponseOrderDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseOrders);
    }
}
