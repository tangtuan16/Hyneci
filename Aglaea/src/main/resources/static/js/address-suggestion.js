document.addEventListener("DOMContentLoaded", function () {
    const addressInput = document.getElementById("addressInput");
    const suggestionList = document.getElementById("suggestionList");
    let timeout = null;

    addressInput.addEventListener("input", function () {
        clearTimeout(timeout);
        const query = addressInput.value.trim();
        if (query.length < 3) {
            suggestionList.innerHTML = "";
            return;
        }

        timeout = setTimeout(() => {
            fetch(`/api/address/suggestions?query=${query}`)
                .then(response => response.json())
                .then(data => {
                    suggestionList.innerHTML = "";
                    data.forEach(address => {
                        const li = document.createElement("li");
                        li.textContent = address;
                        li.onclick = () => {
                            addressInput.value = address;
                            suggestionList.innerHTML = "";
                        };
                        suggestionList.appendChild(li);
                    });
                })
                .catch(err => console.error("Lỗi lấy địa chỉ:", err));
        }, 300); // Debounce 300ms
    });
});
