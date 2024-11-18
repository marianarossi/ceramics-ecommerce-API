package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.dto.Response.ResponseOrderDTO;
import mariana.thePotteryPlace.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService extends ICrudService<Order, Long>{
    OrderDTO saveCompleteOrder(OrderDTO orderDTO);
    ResponseEntity<List<ResponseOrderDTO>> findOrdersByUser();
    ResponseEntity<Page<ResponseOrderDTO>> findPageableOrdersByUser(int page, int size, String order, Boolean asc);
    ResponseOrderDTO findOrderById(Long orderId);
}
