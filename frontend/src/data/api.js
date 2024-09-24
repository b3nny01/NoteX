import API from "../utils/API";

const ip="192.168.1.2"
//const ip="localhost"
const port="8080"

const APIs = {

    // registration features
    registration:       new API("http://"+ip+":"+port+"/notex_backend/registrationServlet","registration","POST"),

    // login features
    login:              new API("http://"+ip+":"+port+"/notex_backend/loginAndAuthServlet","signedUserLogin","POST"),
    logout:             new API("http://"+ip+":"+port+"/notex_backend/loginAndAuthServlet","logout","POST"),  
    retrieveSession:    new API("http://"+ip+":"+port+"/notex_backend/loginAndAuthServlet","getSession","POST"),

    // User features
    search:             new API("http://"+ip+":"+port+"/notex_backend/searchServlet","search","GET"),

    // SignedUser features
    createNote:         new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","createNote","POST"),
    retrieveNotes:      new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","retrieveNotes","GET"),
    updateNote:         new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","updateNote","PUT"),
    deleteNote:         new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","deleteNote","DELETE"),

    createNotebook:     new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","createNotebook","POST"),
    retrieveNotebooks:  new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","retrieveNotebooks","GET"),
    updateNotebook:     new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","updateNotebook","PUT"),
    deleteNotebook:     new API("http://"+ip+":"+port+"/notex_backend/signedUserServlet","deleteNotebook","DELETE"),

    // Review features
    sendReview:         new API("http://"+ip+":"+port+"/notex_backend/reviewServlet","sendReview","POST"),
    retrieveReviews:    new API("http://"+ip+":"+port+"/notex_backend/reviewServlet","retrieveReviews","GET")
};

export default APIs
