import "./SignedUserLoginFeature.css";
import React, { useContext, useState } from "react";
import AuthContext from "../../context/AuthContext";
import api from "../../data/api";
import APIParamter from "../../utils/APIParameter"
import { Link } from "react-router-dom";
import pages from "../../data/pages";

function SignedUserLoginFeature() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [resultDiv, setResultDiv] = useState("");
  const authContextValue = useContext(AuthContext);

  const handleSubmit = (e) => {
    e.preventDefault();
    const apiParams = [
      new APIParamter("username", username),
      new APIParamter("password", password)
    ]
    api.login.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (apiRes.ok) {
          authContextValue.updateSession()
          setResultDiv(<div className="signed-user-login-result-div"><p>
            Login successful, click&nbsp;
            <Link className="page-link" to={pages["virtualDesk"].path}>here</Link>
            &nbsp;to go to your virtual desk</p>
          </div>)
        } else {
          setResultDiv(<div className="signed-user-login-result-div"><p>
            Login not successful, retry or click&nbsp;
            <Link className="page-link" to={pages["search"].path}>here</Link>
            &nbsp;to return to search page</p>
          </div>)
        }
      });
    });
  };

  return (
    <div className="signed-user-login-feature">
      <div className="title">Login</div>
      <form className="signed-user-login-form" onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          value={username}
          onChange={(e) => {
            setUsername(e.target.value);
          }}
          placeholder="username"
        />
        <input
          type="password"
          name="password"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
          placeholder="password"
        />
        <input type="submit" name="submit" value="login" />
      </form>
      {resultDiv}
    </div>
  );
}

export default SignedUserLoginFeature;
