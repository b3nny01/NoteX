// css
import "./NewNotebookModal.css";

// data
import icons from "../../data/icons";

// components
import IconButton from "../../components/IconButton";
import TagListInput from "../../components/TagListInput";


function NewNotebookModal(props) {
  // parameters
  const show=props.show;
  const newNotebook=props.newNotebook;
  const onClose=props.onClose;
  const onNameChange=props.onNameChange;
  const onTagNameChange = props.onTagNameChange;
  const onTagValueChange = props.onTagValueChange;
  const onNewTag=props.onNewTag;
  const onDeleteTag=props.onDeleteTag;
  const onSubmit=props.onSubmit;

  // variables
  const combinedClass="new-notebook-modal-background "+(show?"shown":"hidden");

  // render
  return (
    <div className={ combinedClass}>
      <div className="new-notebook-modal">
        <IconButton icon={icons["cross"]} onClick={onClose}/>
        <div className="title"><p>Notebook Creation</p></div>
        <form className="new-notebook-form" onSubmit={onSubmit}>
          <input
            type="text"
            name="nomeBloccoDiAppunti"
            onChange={onNameChange}
            value={newNotebook.name}
            placeholder="notebook name"
          />
          <TagListInput
            tags={newNotebook.tags}
            onNameChange={onTagNameChange}
            onValueChange={onTagValueChange}
            onNewTag={onNewTag}
            onDeleteTag={onDeleteTag}
          />
          <input type="submit" value="create"/>
        </form>
      </div>
    </div>
  );
}

// export
export default NewNotebookModal;
