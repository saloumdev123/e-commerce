package sen.saloum.saloum_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.StockeDto;
import sen.saloum.saloum_service.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;



    /**
     * Create a new stock entry.
     */
    @PostMapping
    public ResponseEntity<StockeDto> createStock(@Valid @RequestBody StockeDto stockeDto) {
        StockeDto createdStock = stockService.createStock(stockeDto);
        return ResponseEntity.ok(createdStock);
    }

    // Endpoint to manually trigger the low stock check and log notifications
    @GetMapping("/check-low-stock")
    public String checkLowStock() {
        stockService.checkAndNotifyLowStock();
        return "Low stock check complete. Notifications logged if necessary.";
    }

    /**
     * Get a stock entry by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockeDto> getStockById(@PathVariable Long id) {
        StockeDto stockeDto = stockService.getStockById(id);
        return ResponseEntity.ok(stockeDto);
    }

    /**
     * Get all stock entries.
     */
    @GetMapping
    public ResponseEntity<List<StockeDto>> getAllStocks() {
        List<StockeDto> stockeList = stockService.getAllStocks();
        return ResponseEntity.ok(stockeList);
    }

    /**
     * Update a stock entry by ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockeDto> updateStock(@PathVariable Long id, @RequestBody StockeDto stockeDto) {
        StockeDto updatedStock = stockService.updateStock(id, stockeDto);
        return ResponseEntity.ok(updatedStock);
    }

    /**
     * Delete a stock entry by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
