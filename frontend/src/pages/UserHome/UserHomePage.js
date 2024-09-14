// css
import "./UserHomePage.css";

// components
import NavBar from "../../components/NavBar";
import Logo from "../../components/Logo";
import SearchFeature from "./SearchFeature";

function UserHomePage() {
  // render
  return (
    <div className="user-home-page">
      <NavBar currentPage="Search" />
      <Logo />
      <SearchFeature />
    </div>
  );
}

// export
export default UserHomePage;
