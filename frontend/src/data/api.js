import API from "../utils/API";

import APIMock from "../utils/APIMock";
import APIResultMock from "../utils/APIResultMock";
import usersMock from "../utils/usersMock";

// real APIs
const ip="localhost"
const port="8080"

const APIs_REAL = {

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


// mock APIs
let sessionMock = {
    username: "",
    authed: false,
    userType: "USER",
};

const APIs_MOCK = {
    // registration features
    registration: new APIMock((params) => {
      return JSON.stringify(
        new APIResultMock(
          false,
          null,
          "this is a mock version of the website including only the frontend so you can't sign in for real. if you want to try signed user features you can login with the fake credentials 'user1' and 'password'."
        )
      );
    }),
  
    // login features
    login: new APIMock((params) => {
      const username = params.find((p) => {
        return p.name === "username";
      }).value;
      const password = params.find((p) => {
        return p.name === "password";
      }).value;
  
      alert(
        "this is a mock version of the website including only the frontend, you are not logging in for real."
      );
  
      let result = false;
      let msg = " invalid username or password";
      if (username === "user1" && password === "password") {
        result = true;
        sessionMock = {
          username: username,
          authed: true,
          userType: "SIGNED_USER",
        };
        msg = "";
      }
      return JSON.stringify(new APIResultMock(result, sessionMock, msg));
    }),
  
    logout: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you are not logging out for real."
      );
  
      sessionMock = {
        username: "",
        authed: false,
        userType: "USER",
      };
      return JSON.stringify(new APIResultMock(true, sessionMock, ""));
    }),
  
    retrieveSession: new APIMock((params) => {
      return JSON.stringify(new APIResultMock(true, sessionMock, ""));
    }),
  
    // User features
    search: new APIMock((params) => {
      const searchType = params.find((p) => {
        return p.name === "searchType";
      }).value;
      const searchText = params.find((p) => {
        return p.name === "searchText";
      }).value;
      const tags = JSON.parse(
        params.find((p) => {
          return p.name === "tags";
        }).value
      );
  
      let data = [];
      let results = [];
      if (searchType === "signedUsers") {
        data = usersMock;
        results = data.filter((d) => {
          return d.username.startsWith(searchText);
        });
      } else if (searchType === "notebooks") {
        data = usersMock
          .map((u) => {
            return u.notebooks;
          })
          .flat();
        results = data.filter((d) => {
          return d.name.startsWith(searchText);
        });
      } else if (searchType === "notes") {
        data = usersMock
          .map((u) => {
            return u.notebooks;
          })
          .flat()
          .map((nb) => {
            return nb.notes;
          })
          .flat();
        results = data.filter((d) => {
          return d.name.startsWith(searchText);
        });
      }
      return JSON.stringify(
        new APIResultMock(true, { type: searchType, results: results }, "")
      );
    }),
  
    // SignedUser features
    createNote: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't create a note for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
  
    retrieveNotes: new APIMock((params) => {
      const notebookName = params.find((p) => {
        return p.name === "notebookName";
      }).value;
  
      let result = usersMock
        .filter((u) => {
          return u.username === sessionMock.username;
        })
        .map((u) => {
          return u.notebooks;
        })
        .flat()
        .filter((nb) => {
          return nb.name === notebookName;
        })
        .map((nb) => {
          return nb.notes;
        })
        .flat();
      return JSON.stringify(new APIResultMock(true, result, ""));
    }),
  
    updateNote: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't modify a note for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
    deleteNote: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't delete a note for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
  
    createNotebook: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't create a notebook for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
  
    retrieveNotebooks: new APIMock((params) => {
      let result = usersMock
        .filter((u) => {
          return u.username === sessionMock.username;
        })
        .map((u) => {
          return u.notebooks;
        })
        .flat();
      return JSON.stringify(new APIResultMock(true, result, ""));
    }),
  
    updateNotebook: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't modify a notebook for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
  
    deleteNotebook: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't delete a notebook for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
  
    // Review features
    sendReview: new APIMock((params) => {
      alert(
        "this is a mock version of the website including only the frontend, you can't send a review for real."
      );
      return JSON.stringify(new APIResultMock(true, null, ""));
    }),
  
    retrieveReviews: new APIMock((params) => {
      const reviewedUsername = params.find((p) => {
        return p.name === "reviewedUsername";
      }).value;
      const reviewedNotebookName = params.find((p) => {
        return p.name === "reviewedNotebookName";
      }).value;
      const reviewedNoteName = params.find((p) => {
        return p.name === "reviewedNoteName";
      }).value;
  
      let result = usersMock
        .filter((u) => {
          return u.username === reviewedUsername;
        })
        .map((u) => {
          return u.notebooks;
        })
        .flat()
        .filter((nb) => {
          return nb.name === reviewedNotebookName;
        })
        .map((nb) => {
          return nb.notes;
        })
        .flat()
        .find((n) => {
          return n.name === reviewedNoteName;
        }).reviews;
      return JSON.stringify(new APIResultMock(true, result, ""));
    }),
  };




// const APIs = APIs_REAL 
const APIs = APIs_MOCK



export default APIs


