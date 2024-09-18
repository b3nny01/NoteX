import "./RegistrationFeature.css";
import React, { useContext, useState } from "react";
import AuthContext from "../../context/AuthContext";
import APIs from "../../data/api";
import APIParameter from "../../utils/APIParameter";
import { Link } from "react-router-dom";
import pages from "../../data/pages";


function RegistrationFeature() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [resultDiv, setResultDiv] = useState("");
  const authContextValue = useContext(AuthContext);

  const handleSubmit = (e) => {
    e.preventDefault();
    const params = [
      new APIParameter("username", username),
      new APIParameter("password", password)
    ]
    APIs.registration.fetch(params).then((res) => {
      res.json().then((apiResult) => {
        if (!apiResult.ok) {
          alert(apiResult.msg);
          setResultDiv(<div className="registration-result-div"><p>
            Registration not successful, retry changing username or click&nbsp;
            <Link className="page-link" to={pages["search"].path}>here</Link>
            &nbsp;to return to search page</p>
          </div>)
        } else {
          setResultDiv(<div className="registration-result-div"><p>
            Registration successful, click&nbsp;
            <Link className="page-link" to={pages["signedUserLogin"].path}>here</Link>
            &nbsp;to go to login</p>
          </div>)
        }
      });
    });

  };

  return (
    <div className="registration-feature">
      <div className="title">Registration</div>
      <form className="registration-form" onSubmit={handleSubmit}>
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
        <input
          type="password"
          name="confirmPassword"
          value={confirmPassword}
          onChange={(e) => {
            setConfirmPassword(e.target.value);
          }}
          placeholder="confirm password"
        />
        <input type="submit" name="submit" value="sign in" />
      </form>
      {resultDiv}
    </div>
  );
}

export default RegistrationFeature;
