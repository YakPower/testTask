document.addEventListener("DOMContentLoaded", () => {
    const supplierForm = document.getElementById('supplierForm');
    const productForm = document.getElementById('productForm');
    const priceForm = document.getElementById('priceForm');
    const deliveryForm = document.getElementById('deliveryForm');
   // const deliveryItemForm = document.getElementById('deliveryItemForm');
    const reportForm = document.getElementById('reportForm');

    const supplierList = document.getElementById('supplierList');
    const productList = document.getElementById('productList');
    const priceList = document.getElementById('priceList');
    const deliveryList = document.getElementById('deliveryList');
    const reportList = document.getElementById('reportList');

    const priceSupplier = document.getElementById('priceSupplier');
    const priceProduct = document.getElementById('priceProduct');
    const deliverySupplier = document.getElementById('deliverySupplier');
   // const deliveryItemDelivery = document.getElementById('deliveryItemDelivery');
    //const deliveryItemProduct = document.getElementById('deliveryItemProduct');

    const apiUrl = 'http://localhost:8080';


function redirectToDeliveryItemsPage(supplierId) {
    window.location.href = `newDelivery?supplierId=${supplierId}`;
}

    const fetchSuppliers = async () => {
        const response = await axios.get(`${apiUrl}/suppliers`);
        const suppliers = response.data;
        const supplierButtonsDiv = document.getElementById('addDeliveryItemButton');
        supplierList.innerHTML = '';
        priceSupplier.innerHTML = '';
        deliverySupplier.innerHTML = '';
        supplierButtonsDiv.innerHTML = '';

suppliers.forEach(supplier => {
        const button = document.createElement('button');
        button.textContent = `Добавить продукты для ${supplier.name}`;
        button.addEventListener('click', () => {
            redirectToDeliveryItemsPage(supplier.id);
        });
        supplierButtonsDiv.appendChild(button);
    });

        suppliers.forEach(supplier => {
            const li = document.createElement('li');
            li.textContent = supplier.name;
            supplierList.appendChild(li);

            const option1 = document.createElement('option');
            option1.value = supplier.id;
            option1.textContent = supplier.name;
            priceSupplier.appendChild(option1);

            const option2 = document.createElement('option');
            option2.value = supplier.id;
            option2.textContent = supplier.name;
            deliverySupplier.appendChild(option2);

            const option3 = document.createElement('option');
            option3.value = supplier.id;
            option3.textContent = supplier.name;
        });
    };

    const fetchProducts = async () => {
        const response = await axios.get(`${apiUrl}/products`);
        const products = response.data;
        productList.innerHTML = '';
        priceProduct.innerHTML = '';

        products.forEach(product => {
            const li = document.createElement('li');
            li.textContent = `${product.name} (${product.type})`;
            productList.appendChild(li);

            const option1 = document.createElement('option');
            option1.value = product.id;
            option1.textContent = product.name;
            priceProduct.appendChild(option1);

            const option2 = document.createElement('option');
            option2.value = product.id;
            option2.textContent = product.name;
        });
    };

    const fetchPrices = async () => {
        const response = await axios.get(`${apiUrl}/prices`);
        const prices = response.data;
        priceList.innerHTML = '';
        prices.forEach(price => {
            const li = document.createElement('li');
            li.textContent = `Поставщик: ${price.supplier.name}, Продукт: ${price.product.name}, Цена: ${price.price},Период: с ${price.startDate} по ${price.endDate}`;
            priceList.appendChild(li);
        });
    };

    const fetchDeliveries = async () => {
        const response = await axios.get(`${apiUrl}/deliveries`);
        const deliveries = response.data;
        deliveryList.innerHTML = '';
        deliveries.forEach(delivery => {
            const li = document.createElement('li');
            li.textContent = `Поставщик: ${delivery.supplier.name}, Дата: ${delivery.date}`;
            deliveryList.appendChild(li);
        });
    };

    const setPrice = async (supplierId, productId, startDate, endDate, price) => {
        try {
         const response = await axios.get(`${apiUrl}/prices/validate`, {
            params: { supplierId, productId, startDate, endDate }
                });

         if (!response.data) {
            alert("Период пересекается с существующим периодом.");
            return;
         }
console.log('add delivery')
         await axios.post(`${apiUrl}/prices`, { supplier: { id: supplierId }, product: { id: productId }, price, startDate, endDate });
         fetchPrices();
        } catch (error) {
        console.error('Не получилось установить цену:', error);
        }
    };

const setDelivery = async (supplierId, date) => {
try {
         const response = await axios.get(`${apiUrl}/deliveries/validate`, {
            params: { supplierId, date }
                });
         if (!response.data) {
            alert("Данная доставка уже существует.");
            return;
         }
         console.log('add delivery');

        await axios.post(`${apiUrl}/deliveries`, { supplier: { id: supplierId }, date });
         console.log('get deliveries');

         fetchDeliveries();
         console.log('get already');
        } catch (error) {
        console.error('Не получилось добавить доставку:', error);
        }
    };

    supplierForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const name = document.getElementById('supplierName').value;
        await axios.post(`${apiUrl}/suppliers`, { name });
        fetchSuppliers();
    });

    productForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const name = document.getElementById('productName').value;
        const type = document.getElementById('productType').value;
        await axios.post(`${apiUrl}/products`, { name, type });
        fetchProducts();
    });

    priceForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const supplierId = document.getElementById('priceSupplier').value;
        const productId = document.getElementById('priceProduct').value;
        const price = document.getElementById('priceAmount').value;
        const startDate = document.getElementById('priceStartDate').value;
        const endDate = document.getElementById('priceEndDate').value;
        setPrice(supplierId,productId,startDate,endDate,price);
        //await axios.post(`${apiUrl}/prices`, { supplier: { id: supplierId }, product: { id: productId }, price, startDate, endDate });

    });

    deliveryForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const supplierId = document.getElementById('deliverySupplier').value;
        const date = document.getElementById('deliveryDate').value;
        setDelivery(supplierId,date);
        //await axios.post(`${apiUrl}/deliveries`, { supplier: { id: supplierId }, date });
        //fetchDeliveries();
    });



reportForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const startDate = document.getElementById('reportStartDate').value;
    const endDate = document.getElementById('reportEndDate').value;
    const response = await axios.get(`${apiUrl}/deliveries/report`, { params: { startDate, endDate } });
    const reports = response.data;
    reportList.innerHTML = '';

    if (reports.length === 0) {
        reportList.innerHTML = 'Нет данных в выбранном диапазоне.';
        return;
    }

    reports.forEach(report => {
        const li = document.createElement('li');
        let reportText = `<strong>Поставщик:</strong> ${report.supplier}<br>`; // Supplier on a new line

        Object.entries(report.products).forEach(([productName, productData]) => {
            reportText += `<strong>Продукт:</strong> ${productName} - <strong>Общая стоимость:</strong> ${productData.totalCost}, <strong>Количество:</strong> ${productData.quantity}<br>`;
        });

        reportText += `<br><strong>Общая цена за период:</strong> ${report.totalCost}, <strong>Общее количество за период:</strong> ${report.totalQuantity}<br>`;
        li.innerHTML = reportText;
        reportList.appendChild(li);
    });
});
    fetchSuppliers();
    fetchProducts();
    fetchDeliveries();
    fetchPrices();

});
