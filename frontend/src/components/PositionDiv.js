// css
import "./PositionDiv.css";

// assets
import expandSVG from "../assets/icons/expand.svg";

function PositionDiv(props) {
  // parameters
  const steps = props.steps;

  // logic
  const setpDivs = steps.map((s) => {
    return (
      <div className="step-div">
        <div className="name" onClick={s.handleClick}>
          {s.name}
        </div>
        <img className="icon" src={expandSVG} />
      </div>
    );
  });

  // render
  return <div className="position-div">{setpDivs}</div>;
}
// export
export default PositionDiv;
