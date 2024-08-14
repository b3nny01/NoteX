// css
import "./HomeAssistente.css";

// components
import NavBar from "../../components/NavBar";
import Logo from "../../components/Logo";
import SegnalazioniFeature from "./SegnalazioniFeature";

function HomeAssistente() {
  // render
  return (
    <div className="home-assistente-page">
      <div className="header-div">
        <Logo />
        <NavBar currentPage="Libreria Virtuale" />
      </div>

      <div className="content-div">
        <SegnalazioniFeature />
      </div>
    </div>
  );
}

export default HomeAssistente;
