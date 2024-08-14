// css
import "./IconButton.css";

function IconButton(props) {
  // paramters
  const icon = props.icon;
  const onClick = props.onClick;

  // variables
  const combinedClass = icon.name + " icon-button " + icon.theme;

  // render
  return (
    <div className={combinedClass} onClick={onClick}>
      <img className="icon-img" src={icon.src} />
    </div>
  );
}

// export
export default IconButton;
