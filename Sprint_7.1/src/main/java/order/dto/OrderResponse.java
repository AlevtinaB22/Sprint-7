package order.dto;
import lombok.Data;
import order.dto.forresponse.ArrayOrders;
import order.dto.forresponse.AvailableStations;
import order.dto.forresponse.PageInfo;

@Data
public class OrderResponse {
    ArrayOrders[] arrayOrders;
    PageInfo pageInfo;
    AvailableStations[] availableStations;
}
