package order.dto.forresponse;
import lombok.Data;

@Data
public class PageInfo {
    private int page;
    private int total;
    private int limit;
}
