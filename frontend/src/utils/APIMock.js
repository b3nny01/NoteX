class APIMock {
    constructor(fetchMock) {
        this.fetchMock=fetchMock
    }
    fetch(params) {
        return new Promise((resolve,reject)=>{resolve(new Response(this.fetchMock(params)));});
    }

}

export default APIMock;
