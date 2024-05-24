     document.addEventListener("DOMContentLoaded", () => {
            const apiUrl = 'http://localhost:8080';

            const addItemForm = document.getElementById('addItemForm');
            const deliveryDateSelect = document.getElementById('deliveryDate');
            const productInputsContainer = document.getElementById('productInputs');

            const urlParams = new URLSearchParams(window.location.search);



            const fetchDeliveryDates = async (supplierId) => {
                try {
                    const response = await axios.get(`${apiUrl}/deliveries/dates?supplierId=${supplierId}`);
                    const deliveryDates = response.data;
                    deliveryDates.forEach(delivery  => {
                        const option = document.createElement('option');
                        option.value = delivery.id;
                        option.textContent = delivery.date;
                        deliveryDateSelect.appendChild(option);
                    });
                } catch (error) {
                    console.error('Ошибка получения доступных дат поставок:', error);
                }
            };

            const fetchProductPrices = async (supplierId,date) => {
    try {
        const response = await axios.get(`${apiUrl}/prices/availableProductPrices?supplierId=${supplierId}&date=${date}`);
        const productPrices = response.data;
        productInputsContainer.innerHTML = '';
        productPrices.forEach(result => {
            const productId = result[0];
            const productName = result[1];
            const price = result[2];

            const inputLabel = document.createElement('label');
            inputLabel.textContent = productName + ':';

            const inputField = document.createElement('input');
            inputField.type = 'number';
            inputField.name = productId;
            inputField.min = 0;
            inputField.required = true;

            productInputsContainer.appendChild(inputLabel);
            productInputsContainer.appendChild(inputField);
            productInputsContainer.appendChild(document.createElement('br'));
        });
    } catch (error) {
        console.error('Ошибка получения цен:', error);
    }
};




           addItemForm.addEventListener('submit', async (event) => {
               event.preventDefault();
               const supplierId = urlParams.get('supplierId');
               const deliveryDate = deliveryDateSelect.value;


        const productQuantities = {};
     const inputs = productInputsContainer.getElementsByTagName('input');
     Array.from(inputs).forEach(input => {
         productQuantities[input.name] = parseInt(input.value);
     });

     const deliveryItems = Object.keys(productQuantities).map(productId => ({
         productId: parseInt(productId),
         quantity: productQuantities[productId]
     }));

     try {
         const response = await axios.post(`${apiUrl}/deliveries/${deliveryDateSelect.value}/items`, deliveryItems);
         console.log('Продукты добавлены:', response.data);
         alert("Добавлено");
     } catch (error) {
         console.error('Ошибка добавления:', error);
         alert("Ошибка");
     }
 });

document.getElementById("goBackButton").addEventListener("click", function() {
  window.history.back();
});


           deliveryDateSelect.addEventListener('change', async (event) => {
           const selectedOption = event.target.options[event.target.selectedIndex];
               if (!selectedOption.disabled) {
                   const date = selectedOption.textContent.trim();
                   console.log(date);
                   await fetchProductPrices(urlParams.get('supplierId'),date);
                   }


           });

        fetchDeliveryDates(urlParams.get('supplierId'));
        });