// css
import "./NavBar.css";

// context
import AuthContext from "../context/AuthContext";

// hooks
import { useContext, useState } from "react";

// data
import pages from "../data/pages";
import icons from "../data/icons";

// components
import IconDiv from "./IconDiv";
import { Link } from "react-router-dom";
import api from "../data/api";

function NavBar(props) {
  // hooks
  const [isExpanded, setExpanded] = useState(false);
  const authContextValue = useContext(AuthContext);

  // parameters
  const currentPage = props.currentPage;

  // logic
  const pageLinks = Object.keys(pages).map((k, i) => {
    return (
      <Link
        to={pages[k].path}
        key={pages[k].name}
        className={
          "page-link " + (pages[k].name === currentPage ? "current" : "")
        }
      >
        {pages[k].name}
      </Link>
    );
  });

  // render
  return (
    <div className="nav-bar-div">
      {!authContextValue.userSession.authed && (
        <Link to={pages["search"].path} className="option">
          <p>{pages["search"].name}</p>
        </Link>
      )}
      {!authContextValue.userSession.authed && (
        <Link to={pages["signedUserLogin"].path} className="option">
          <p>{pages["signedUserLogin"].name}</p>
        </Link>
      )}
      {!authContextValue.userSession.authed && (
        <Link to={pages["registration"].path} className="option">
          <p>{pages["registration"].name}</p>
        </Link>
      )}
      {authContextValue.userSession.authed && (
        <label className="hamburger-label option">
          <input
            type="checkbox"
            checked={isExpanded}
            onChange={() => {
              setExpanded(!isExpanded);
            }}
          />
          <IconDiv icon={icons["hamburger"]} />
          {authContextValue.userSession.authed && (
            <div className="pages-div">{pageLinks}</div>
          )}
        </label>
      )}
      {authContextValue.userSession.authed && (
        <div
          className="option"
          onClick={() => {
            api.logout.fetch([]).then((res) => {
              res.json().then((apiRes) => {
                if (!apiRes.ok) alert(apiRes.msg);
                authContextValue.updateSession();
              });
            });
          }}
        >
        <p>logout</p>
        </div>
      )}
    </div>
  );
}

// export
export default NavBar;
