package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.dto.OrderDTO;
import mariana.thePotteryPlace.dto.OrderItemDTO;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.OrderItem;
import mariana.thePotteryPlace.model.Product;
import mariana.thePotteryPlace.repository.OrderItemRepository;
import mariana.thePotteryPlace.repository.OrderRepository;
import mariana.thePotteryPlace.repository.ProductRepository;
import mariana.thePotteryPlace.repository.UserRepository;
import mariana.thePotteryPlace.service.AuthService;
import mariana.thePotteryPlace.service.IOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AuthService authService;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AuthService authService, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.authService = authService;
        this.productRepository = productRepository;
    }

   public OrderDTO saveCompleteOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(authService.getAuthenticatedUser());
        order.setStatus("Waiting confirmation");
        order.setPayment(orderDTO.getPayment());
        order.setAddress(orderDTO.getAddress());
        order.setShipping(orderDTO.getShipping());
        orderRepository.save(order);

        for(OrderItemDTO dtItem : orderDTO.getItems())
        {
            Product product = productRepository.findById(dtItem.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setQuantity(dtItem.getQuantity());
            orderItemRepository.save(item);
        }

        orderDTO.setId(order.getId());
        return orderDTO;
   }

    public List<Order> findOrdersByUser() {
        Long authenticatedUserId = authService.getAuthenticatedUser().getId();
        return orderRepository.findOrdersByUserWithItems(authenticatedUserId);
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }
}
