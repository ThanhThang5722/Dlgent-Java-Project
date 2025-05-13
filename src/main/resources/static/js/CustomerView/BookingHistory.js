// Switch between tabs
function switchTab(tabId) {
    console.log('Switching to tab:', tabId);

    // Hide all tab contents
    document.querySelectorAll('.tab-content').forEach(content => {
        content.style.display = 'none';
    });

    // Remove active class from all tabs
    document.querySelectorAll('.tab-item').forEach(tab => {
        tab.classList.remove('active');
    });

    // Show selected tab content
    document.getElementById('content-' + tabId).style.display = 'block';

    // Add active class to selected tab
    document.getElementById('tab-' + tabId).classList.add('active');
}

// Add event listeners when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', function () {
    console.log('DOM fully loaded');

    // Log all tab elements found
    const tabElements = document.querySelectorAll('.tab-item');
    console.log('Found tab elements:', tabElements.length);

    // Add click event listeners to all tab items
    tabElements.forEach(tab => {
        console.log('Adding click listener to tab:', tab.id);

        tab.addEventListener('click', function () {
            const tabId = this.id.replace('tab-', '');
            console.log('Tab clicked:', tabId);
            switchTab(tabId);
        });
    });

    // Không cần xử lý sự kiện click cho các nút "View booking" nữa
    // vì chúng ta đã thay đổi thành thẻ <a> với href trực tiếp

    // Set the initial active tab
    console.log('Setting initial tab to completed');
    switchTab('completed');

    // Add a direct click handler to the tab header as a fallback
    document.querySelector('.tab-header').addEventListener('click', function (e) {
        if (e.target.classList.contains('tab-item')) {
            const tabId = e.target.id.replace('tab-', '');
            console.log('Tab header click detected on:', tabId);
            switchTab(tabId);
        }
    });
});
