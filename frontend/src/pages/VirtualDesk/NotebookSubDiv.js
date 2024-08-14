// css
import "./NotebookSubDiv.css";

// data
import icons from "../../data/icons";

// components
import IconButton from "../../components/IconButton";

function NotebookSubDiv(props) {
  // parameters
  const index = props.index;
  const notebook = props.notebook;
  const onSelectNotebook = props.onSelectNotebook;
  const onDeleteNotebook = props.onDeleteNotebook;
  const onUpdateNotebook = props.onUpdateNotebook;

  // render
  return (
    <div className="notebook-sub-div">
      <div className="icon-name-div">
        <img
          className="icon-img"
          src={icons["notebook"].src}
          onClick={(e) => {
            onSelectNotebook(notebook.name);
          }}
        />
        <div className="name">{notebook.name}</div>
      </div>

      <div className="actions">
        <IconButton
          icon={icons["delete"]}
          onClick={(e) => {
            onDeleteNotebook(index);
          }}
        />
        <IconButton icon={icons["edit"]}
          onClick={(e) => {
            onUpdateNotebook(index, e);
          }} />
      </div>
    </div>
  );
}

// export
export default NotebookSubDiv;
