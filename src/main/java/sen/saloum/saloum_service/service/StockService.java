package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sen.saloum.saloum_service.domain.Stocke;
import sen.saloum.saloum_service.models.dto.StockeDto;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.repos.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {


    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    // Method to check for low stock and log notifications
    public void checkAndNotifyLowStock() {
        // Fetch low stock items from the repository
        List<Stocke> lowStockItems = stockRepository.findLowStock();

        // Check if there are any low stock items
        if (!lowStockItems.isEmpty()) {
            for (Stocke stock : lowStockItems) {
                // Log the low stock notification
                logLowStockNotification(stock);
            }
        } else {
            log.info("No low stock items found.");
        }
    }

    // Log a notification for low stock
    private void logLowStockNotification(Stocke stock) {
        log.warn("Low stock alert: Product ID " + stock.getId() + " is running low. Only "
                + stock.getQuantiteDisponible() + " items left, below the threshold of "
                + stock.getSeuilAlerte() + ".");
    }

    /**
     * Create a new stock entry.
     */
    @Transactional
    public StockeDto createStock(StockeDto stockeDto) {
        var product = productRepository.findById(stockeDto.getProduct())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + stockeDto.getProduct() + " not found"));

        Stocke stocke = new Stocke();
        stocke.setQuantiteDisponible(stockeDto.getQuantiteDisponible());
        stocke.setSeuilAlerte(stockeDto.getSeuilAlerte());
        stocke.setProduct(product);

        Stocke savedStocke = stockRepository.save(stocke);

        stockeDto.setId(savedStocke.getId());
        return stockeDto;
    }

    /**
     * Get a stock entry by ID.
     */
    public StockeDto getStockById(Long id) {
        Stocke stocke = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock with ID " + id + " not found"));

        return mapToDto(stocke);
    }

    /**
     * Get all stock entries.
     */
    public List<StockeDto> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a stock entry.
     */
    @Transactional
    public StockeDto updateStock(Long id, StockeDto stockeDto) {
        Stocke stocke = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock with ID " + id + " not found"));

        var product = productRepository.findById(stockeDto.getProduct())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + stockeDto.getProduct() + " not found"));

        stocke.setQuantiteDisponible(stockeDto.getQuantiteDisponible());
        stocke.setSeuilAlerte(stockeDto.getSeuilAlerte());
        stocke.setProduct(product);

        Stocke updatedStocke = stockRepository.save(stocke);
        return mapToDto(updatedStocke);
    }

    /**
     * Delete a stock entry by ID.
     */
    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new IllegalArgumentException("Stock with ID " + id + " not found");
        }
        stockRepository.deleteById(id);
    }

    /**
     * Map Stocke entity to StockeDto.
     */
    private StockeDto mapToDto(Stocke stocke) {
        return new StockeDto(
                stocke.getId(),
                stocke.getQuantiteDisponible(),
                stocke.getSeuilAlerte(),
                stocke.getProduct().getId()
        );
    }
}
