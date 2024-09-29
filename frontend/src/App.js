import "./App.css";
import { BrowserRouter, Routes, Route, HashRouter } from "react-router-dom";
import { useEffect, useState } from "react";

import api from "./data/api";

import AuthContext from "./context/AuthContext";
import AuthErrorPage from "./pages/AuthError/AuthErrorPage";
import UserHomePage from "./pages/UserHome/UserHomePage";
import SignedUserLoginPage from "./pages/SignedUserLogin/SignedUserLoginPage";
import RegistrationPage from "./pages/Registration/RegistrationPage";
import VirtualDeskPage from "./pages/VirtualDesk/VirtualDeskPage";
import SendReviewPage from "./pages/SendReview/SendReviewPage";

function App() {
  const [userSession, setUserSession] = useState({
    username: "",
    authed: false,
    userType: "USER",
  });

  const handleSessionUpdate = () => {
    api.retrieveSession.fetch([]).then((res) => {
      res.json().then(apiRes => {
        if (apiRes.ok) {
          setUserSession(apiRes.obj);
        } else {
          alert(apiRes.msg);
        }
      });
    });
  };

  useEffect(handleSessionUpdate, []);

  const authContextValue = {
    userSession: userSession,
    updateSession: handleSessionUpdate,
  };

  return (
    <AuthContext.Provider value={authContextValue}>
      <HashRouter basename="/" >
        <Routes>
          <Route index element={<UserHomePage />} />
          <Route
            path="/VirtualDesk"
            element={
              (
                authContextValue.userSession.authed &&
                authContextValue.userSession.userType === "SIGNED_USER"
              ) ? (
                <VirtualDeskPage />
              ) : (
                <AuthErrorPage />
              )
            }
          />
          <Route
            path="/SendReview/:usernameURL/:notebookURL/:noteURL"
            element={
              (
                authContextValue.userSession.authed &&
                authContextValue.userSession.userType === "SIGNED_USER"
              ) ? (
                <SendReviewPage />
              ) : (
                <AuthErrorPage />
              )
            }
          />
          <Route
            path="/SendReview"
            element={
              (
                authContextValue.userSession.authed &&
                authContextValue.userSession.userType === "SIGNED_USER"
              ) ? (
                <SendReviewPage />
              ) : (
                <AuthErrorPage />
              )
            }
          />
          <Route
            path="/Assistente"
            element={
              (
                authContextValue.userSession.authed &&
                authContextValue.userSession.userType === "ASSISTENTE"
              ) ? (
                <UserHomePage />
              ) : (
                <AuthErrorPage />
              )
            }
          />
          <Route
            path="/Amministratore"
            element={
              (
                authContextValue.userSession.authed &&
                authContextValue.userSession.userType === "AMMINISTRATORE"
              ) ? (
                <UserHomePage />
              ) : (
                <AuthErrorPage />
              )
            }
          />
          <Route path="/Login" element={<SignedUserLoginPage />} />
          <Route path="/AccessoAssistente" element={<UserHomePage />} />
          <Route path="/AccessoAmministratore" element={<UserHomePage />} />
          <Route path="/Registration" element={<RegistrationPage />} />
          <Route path="/AuthError" element={<AuthErrorPage />} />
        </Routes>
      </HashRouter>
    </AuthContext.Provider>
  );
}

export default App;
