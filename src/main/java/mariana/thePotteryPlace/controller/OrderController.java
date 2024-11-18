package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.request.OrderDTO;
import mariana.thePotteryPlace.dto.response.ResponseOrderDTO;
import mariana.thePotteryPlace.error.AccessDeniedException;
import mariana.thePotteryPlace.error.ResourceNotFoundException;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.service.AuthService;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController extends CrudController<Order, OrderDTO, ResponseOrderDTO,Long>{
    private final IOrderService orderService;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    public OrderController(IOrderService orderService, ModelMapper modelMapper, AuthService authService) {
        super(Order.class, OrderDTO.class, ResponseOrderDTO.class);
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.authService = authService;
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
    public ResponseEntity<ResponseOrderDTO> update(Long id, OrderDTO dto) {
        // Retrieve the order by ID
        Order order = getService().findOne(id);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Fetch the currently authenticated user
        User currentUser = authService.getAuthenticatedUser();

        // Validate that the current user is the owner of the order
        if (!order.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // User is not authorized to update this order
        }

        // Map the updated DTO to the order entity
        getModelMapper().map(dto, order);

        // Save the updated order entity
        Order updatedOrder = getService().save(order);

        // Map the updated order entity to the response DTO
        ResponseOrderDTO responseDTO = getModelMapper().map(updatedOrder, ResponseOrderDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            orderService.deleteOrderIfOwner(id); // Call the service method to handle the validation and deletion
            return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if order not found
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if the user is not authorized
        }
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
