package mariana.thePotteryPlace.service.impl;


import mariana.thePotteryPlace.model.OrderItem;
import mariana.thePotteryPlace.repository.OrderItemRepository;
import mariana.thePotteryPlace.service.IOrderItemService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService extends CrudServiceImpl<OrderItem, Long> implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    protected JpaRepository<OrderItem, Long> getRepository() {
        return orderItemRepository;
    }
}
