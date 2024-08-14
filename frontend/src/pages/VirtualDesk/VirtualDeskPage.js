// css
import "./VirtualDeskPage.css";

// components
import NavBar from "../../components/NavBar";
import Logo from "../../components/Logo";
import VirtualDeskStage from "./VirtualDeskStage";

function VirtualDeskPage() {
  // render
  return (
    <div className="virtual-desk-page">
      <div className="header-div">
        <Logo />
        <NavBar currentPage="Virtual Desk" />
      </div>

      <div className="content-div">
        <VirtualDeskStage/>
      </div>
    </div>
  );
}

export default VirtualDeskPage;
