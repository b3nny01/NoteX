// css
import "./VirtualDeskStage.css";

// hooks
import { useState, useEffect } from "react";

// components
import PositionDiv from "../../components/PositionDiv";
import VirtualDeskFeature from "./VirtualDeskFeature";
import NotebookFeature from "./NotebookFeature";

function VirtualDeskStage(props) {
  // state
  const [onStage, setOnStage] = useState("virtualDesk");
  const [selected, setSelected] = useState({
    notebook: "",
    note: "",
  });
  const [steps, setSteps] = useState([
    {
      name: "Virtual Desk",
      handleClick: () => {
        handleSelectVirtualDesk();
      },
    },
  ]);

  // variables
  let onStageDiv = <div />;

  // methods
  const handleSelectVirtualDesk = () => {
    setOnStage("virtualDesk");
    setSelected({
      notebook: "",
      noteIndex: "",
    });
    setSteps([
      {
        name: "Virtual Desk",
        handleClick: () => {
          handleSelectVirtualDesk();
        },
      },
    ]);
  };

  const handleSelectNotebook = (notebook) => {
    setOnStage("notebook");
    setSelected({
      notebook: notebook,
      notaIndex: "",
    });
    setSteps([
      {
        name: "Virtual Desk",
        handleClick: () => {
          handleSelectVirtualDesk();
        },
      },
      {
        name: notebook,
        handleClick: () => {
          handleSelectNotebook(notebook);
        },
      },
    ]);
  };

  // logic
  if (onStage === "virtualDesk") {
    // render
    onStageDiv = <VirtualDeskFeature onSelectNotebook={handleSelectNotebook} />;
  } else if (onStage === "notebook") {
    // render
    onStageDiv = (
      <NotebookFeature
        notebookName={selected.notebook}
      />
    );
  }
  // render
  return (
    <div className="virtual-desk-stage">
      <PositionDiv steps={steps} />
      {onStageDiv}
    </div>
  );
}

// export
export default VirtualDeskStage;
