package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.dto.OrderItemDTO;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.OrderItem;
import mariana.thePotteryPlace.repository.OrderItemRepository;
import mariana.thePotteryPlace.repository.OrderRepository;
import mariana.thePotteryPlace.service.IOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

   public OrderDTO saveCompleteOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setDate(new Date());
        order.setUser(orderDTO.getUser());
        orderRepository.save(order);

        for(OrderItemDTO dtItem : orderDTO.getItems())
        {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPrice(dtItem.getPrice());
            item.setProduct(dtItem.getProduct());
            item.setQuantity(dtItem.getQuantity());
            orderItemRepository.save(item);
        }

        orderDTO.setId(order.getId());
        return orderDTO;
   }

    @Override
    public List<Order> findAll() {
        return super.findAll();
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }
}
