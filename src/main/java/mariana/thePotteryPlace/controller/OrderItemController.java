package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.OrderItemDTO;
import mariana.thePotteryPlace.dto.Response.ResponseOrderItemDTO;
import mariana.thePotteryPlace.model.OrderItem;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IOrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order-item")
public class OrderItemController extends CrudController<OrderItem, OrderItemDTO, ResponseOrderItemDTO,Long>{
    private final IOrderItemService orderItemService;
    private final ModelMapper modelMapper;

    public OrderItemController(IOrderItemService orderItemService, ModelMapper modelMapper) {
        super(OrderItem.class, OrderItemDTO.class, ResponseOrderItemDTO.class);
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected ICrudService<OrderItem, Long> getService() {
        return this.orderItemService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
