package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.model.Order;

import java.util.List;

public interface IOrderService extends ICrudService<Order, Long>{
    OrderDTO saveCompleteOrder(OrderDTO orderDTO);
    List<Order> findOrdersByUser();
}
