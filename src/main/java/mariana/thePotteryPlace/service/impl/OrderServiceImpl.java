package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.dto.request.OrderDTO;
import mariana.thePotteryPlace.dto.request.OrderItemDTO;
import mariana.thePotteryPlace.dto.response.ResponseOrderDTO;
import mariana.thePotteryPlace.dto.response.ResponseOrderItemDTO;
import mariana.thePotteryPlace.error.AccessDeniedException;
import mariana.thePotteryPlace.error.ResourceNotFoundException;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.OrderItem;
import mariana.thePotteryPlace.model.Product;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.repository.OrderItemRepository;
import mariana.thePotteryPlace.repository.OrderRepository;
import mariana.thePotteryPlace.repository.ProductRepository;
import mariana.thePotteryPlace.repository.UserRepository;
import mariana.thePotteryPlace.service.AuthService;
import mariana.thePotteryPlace.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AuthService authService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AuthService authService, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.authService = authService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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

        orderDTO.setUserid(order.getUser().getId());
        return orderDTO;
   }

    public void deleteOrderIfOwner(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new
                    ResourceNotFoundException("Order not found"); // You can throw a custom exception for not found
        }

        User authenticatedUser = authService.getAuthenticatedUser(); // Get the authenticated user

        if (!order.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this order"); // Custom exception for access denial
        }

        orderItemRepository.deleteByOrderId(orderId);


        orderRepository.delete(order); // Proceed with the deletion
    }


    public ResponseOrderDTO findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        // Convert Order and OrderItems to DTOs
        ResponseOrderDTO responseOrderDTO = modelMapper.map(order, ResponseOrderDTO.class);

        List<ResponseOrderItemDTO> itemDTOs = orderItems.stream()
                .map(item -> {
                    ResponseOrderItemDTO dto = new ResponseOrderItemDTO();
                    dto.setProductName(item.getProduct().getTitle());
                    dto.setPrice(item.getPrice());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                })
                .toList();

        responseOrderDTO.setItems(itemDTOs);
        return responseOrderDTO;
    }

    @Override
    public ResponseEntity<List<ResponseOrderDTO>> findOrdersByUser() {
        // Fetch the currently authenticated user (you can get the user from the security context or session)
        User currentUser = authService.getAuthenticatedUser();

        // Fetch all orders for the authenticated user
        List<Order> userOrders = orderRepository.findByUser(currentUser);

        // Map each Order to ResponseOrderDTO
        List<ResponseOrderDTO> responseOrders = userOrders.stream()
                .map(order -> {
                    // Convert Order to ResponseOrderDTO
                    ResponseOrderDTO orderDTO = modelMapper.map(order, ResponseOrderDTO.class);

                    // Fetch associated OrderItems for this order
                    List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

                    // Map OrderItems to ResponseOrderItemDTO and set in the orderDTO
                    List<ResponseOrderItemDTO> orderItemDTOs = orderItems.stream()
                            .map(item -> {
                                ResponseOrderItemDTO itemDTO = new ResponseOrderItemDTO();
//                                itemDTO.setProductId(item.getProduct().getId());
                                itemDTO.setProductName(item.getProduct().getTitle());
                                itemDTO.setPrice(item.getPrice());
                                itemDTO.setQuantity(item.getQuantity());
                                return itemDTO;
                            })
                            .collect(Collectors.toList());

                    orderDTO.setItems(orderItemDTOs);
                    return orderDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseOrders);
    }

    @Override
    public ResponseEntity<Page<ResponseOrderDTO>> findPageableOrdersByUser(int page, int size, String sortBy, Boolean asc) {
        // Fetch the currently authenticated user
        User currentUser = authService.getAuthenticatedUser();

        // Create a Pageable object with sorting
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated orders for the authenticated user
        Page<Order> userOrders = orderRepository.findByUser(currentUser, pageable);

        // Map each Order to ResponseOrderDTO
        Page<ResponseOrderDTO> responseOrders = userOrders.map(order -> {
            // Convert Order to ResponseOrderDTO
            ResponseOrderDTO orderDTO = modelMapper.map(order, ResponseOrderDTO.class);

            // Fetch associated OrderItems for this order
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

            // Map OrderItems to ResponseOrderItemDTO and set in the orderDTO
            List<ResponseOrderItemDTO> orderItemDTOs = orderItems.stream()
                    .map(item -> {
                        ResponseOrderItemDTO itemDTO = new ResponseOrderItemDTO();
                        itemDTO.setProductName(item.getProduct().getTitle());
                        itemDTO.setPrice(item.getPrice());
                        itemDTO.setQuantity(item.getQuantity());
                        return itemDTO;
                    })
                    .collect(Collectors.toList());

            orderDTO.setItems(orderItemDTOs);
            return orderDTO;
        });

        // Return the paginated response
        return ResponseEntity.ok(responseOrders);
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }
}
