// css
import "./HomeAmministratorePage.css";

// components
import NavBar from "../../components/NavBar";
import Logo from "../../components/Logo";
import AssistentiFeature from "./AssistentiFeature";
import UtentiRimossiFeature from "./UtentiRimossiFeature";

function HomeAmministratorePage() {
  // render
  return (
    <div className="home-amministratore-page">
      <div className="header-div">
        <Logo />
        <NavBar currentPage="Libreria Virtuale" />
      </div>

      <div className="content-div">
        <AssistentiFeature />
        <UtentiRimossiFeature />
      </div>
    </div>
  );
}

export default HomeAmministratorePage;
