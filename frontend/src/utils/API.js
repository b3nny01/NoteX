class API {
    constructor(baseUrl, action, method) {
        this.baseUrl = baseUrl;
        this.action = action;
        this.method = method;
    }
    fetch(params) {
        if (this.method === "GET") {
            let formData = "action=" + this.action;
            params.forEach(p => {
                formData += "&"
                formData += p.name;
                formData += "=";
                formData += p.value;
            });

            return fetch(
                this.baseUrl + "?" + formData,
                {
                    method: this.method,
                    mode: "cors",
                    headers: {
                        "Content-type": "application/x-www-form-urlencoded;charset=UTF-8",
                    },
                    credentials: "include",
                }
            );
        } else {
            let formData = new FormData();
            formData.append("action", this.action);
            params.forEach(p => {
                formData.append(p.name, p.value);
            });

            return fetch(
                this.baseUrl,
                {
                    method: this.method,
                    mode: "cors",
                    credentials: "include",
                    body: formData
                }
            );

        }


    }

}

export default API;
