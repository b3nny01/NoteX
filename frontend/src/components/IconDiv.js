// css
import "./IconDiv.css";

function IconDiv(props) {
  // parameters
  const icon = props.icon;

  // variables
  const combinedClass = icon.name + " icon-div " + icon.theme;

  // render
  return (
    <div className={combinedClass}>
      <img className="icon-img" src={icon.src} />
    </div>
  );
}

// export
export default IconDiv;
