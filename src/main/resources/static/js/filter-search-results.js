document.addEventListener('DOMContentLoaded', function() {
    console.log('Filter script loaded');
    
    // Get all resort cards
    const resortCards = document.querySelectorAll('.resort-card, .card');
    console.log('Found resort cards:', resortCards.length);
    
    // Initialize counters
    const totalCount = resortCards.length;
    updateCounters(totalCount, totalCount);
    
    // Set up price range slider
    const priceRangeSlider = document.getElementById('priceRange');
    if (priceRangeSlider) {
        const priceValueDisplay = document.getElementById('priceRangeValue');
        
        // Set initial value
        if (priceValueDisplay) {
            priceValueDisplay.textContent = formatCurrency(priceRangeSlider.value);
        }
        
        // Update on change
        priceRangeSlider.addEventListener('input', function() {
            if (priceValueDisplay) {
                priceValueDisplay.textContent = formatCurrency(this.value);
            }
            applyFilters();
        });
    }
    
    // Set up filter checkboxes
    const filterCheckboxes = document.querySelectorAll('.filter-check');
    filterCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', applyFilters);
    });
    
    // Set up reset button
    const resetButton = document.getElementById('resetFilters');
    if (resetButton) {
        resetButton.addEventListener('click', resetFilters);
    }
    
    // Apply filters function
    function applyFilters() {
        console.log('Applying filters');
        
        // Get filter values
        const maxPrice = priceRangeSlider ? parseInt(priceRangeSlider.value) : Infinity;
        
        // Get checkbox states
        const filters = {
            freeCancel: document.getElementById('freeCancel')?.checked || false,
            breakfast: document.getElementById('breakfast')?.checked || false,
            wifi: document.getElementById('wifi')?.checked || false,
            pool: document.getElementById('pool')?.checked || false,
            parking: document.getElementById('parking')?.checked || false
        };
        
        console.log('Filter values:', { maxPrice, ...filters });
        
        let visibleCount = 0;
        
        // Apply filters to each card
        resortCards.forEach(card => {
            // Extract price
            let price = Infinity;
            const priceElements = card.querySelectorAll('.price, .current-price, .room-price');
            
            priceElements.forEach(el => {
                const priceText = el.textContent;
                const extractedPrice = extractPrice(priceText);
                if (extractedPrice < price) {
                    price = extractedPrice;
                }
            });
            
            // If no price found, try to find it in any element
            if (price === Infinity) {
                const allText = card.textContent;
                price = extractPrice(allText);
            }
            
            console.log('Card price:', price);
            
            // Check price filter
            const priceMatch = price <= maxPrice;
            
            // Check amenities
            const cardText = card.textContent.toLowerCase();
            const amenities = {
                freeCancel: cardText.includes('hủy miễn phí') || cardText.includes('free cancel'),
                breakfast: cardText.includes('bữa sáng') || cardText.includes('breakfast'),
                wifi: cardText.includes('wifi') || cardText.includes('internet'),
                pool: cardText.includes('hồ bơi') || cardText.includes('bể bơi') || cardText.includes('pool'),
                parking: cardText.includes('đậu xe') || cardText.includes('đỗ xe') || cardText.includes('parking')
            };
            
            // Check if card matches all selected filters
            const amenityMatch = Object.keys(filters).every(key => {
                return !filters[key] || amenities[key];
            });
            
            // Show/hide card
            if (priceMatch && amenityMatch) {
                card.style.display = '';
                visibleCount++;
            } else {
                card.style.display = 'none';
            }
        });
        
        // Update counters
        updateCounters(visibleCount, totalCount);
        
        // Show/hide no results message
        const noResultsMsg = document.getElementById('noResultsMessage');
        if (noResultsMsg) {
            noResultsMsg.style.display = visibleCount === 0 ? 'block' : 'none';
        }
    }
    
    // Reset filters function
    function resetFilters() {
        console.log('Resetting filters');
        
        // Reset price range
        if (priceRangeSlider) {
            priceRangeSlider.value = priceRangeSlider.max;
            const priceValueDisplay = document.getElementById('priceRangeValue');
            if (priceValueDisplay) {
                priceValueDisplay.textContent = formatCurrency(priceRangeSlider.max);
            }
        }
        
        // Reset checkboxes
        filterCheckboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
        
        // Show all cards
        resortCards.forEach(card => {
            card.style.display = '';
        });
        
        // Update counters
        updateCounters(totalCount, totalCount);
        
        // Hide no results message
        const noResultsMsg = document.getElementById('noResultsMessage');
        if (noResultsMsg) {
            noResultsMsg.style.display = 'none';
        }
    }
    
    //update counters
    function updateCounters(visible, total) {
        const visibleCountEl = document.getElementById('visibleCount');
        const totalCountEl = document.getElementById('totalCount');
        
        if (visibleCountEl) visibleCountEl.textContent = visible;
        if (totalCountEl) totalCountEl.textContent = total;
    }
    
    // extract price from text
    function extractPrice(text) {
        // Remove all non-digit characters and parse as integer
        const matches = text.match(/\d[\d\s,.]*\d/g);
        if (matches && matches.length > 0) {
            return parseInt(matches[0].replace(/\D/g, ''));
        }
        return Infinity;
    }
    
    //format currency
    function formatCurrency(value) {
        return parseInt(value).toLocaleString('vi-VN') + '₫';
    }
});