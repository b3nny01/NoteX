// css
import "./Logo.css";

// resources
import icons from "../data/icons";

function Logo() {
  // render
  return (
    <div className="logo">
      <img src={icons["logo"].src} alt="img" />
      <div>NoteX</div>
    </div>
  );
}

// export
export default Logo;
