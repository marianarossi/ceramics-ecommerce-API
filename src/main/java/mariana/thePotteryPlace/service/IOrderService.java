package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.model.Order;

public interface IOrderService extends ICrudService<Order, Long>{
    OrderDTO saveCompleteOrder(OrderDTO orderDTO);
}
