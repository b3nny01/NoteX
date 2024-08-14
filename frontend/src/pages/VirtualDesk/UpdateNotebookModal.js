// css
import "./UpdateNotebookModal.css";

// data
import icons from "../../data/icons";

// components
import IconButton from "../../components/IconButton";
import TagListInput from "../../components/TagListInput";


function UpdateNotebookModal(props) {
  // parameters
  const show=props.show;
  const index=props.index;
  const notebook=props.notebook;
  const onClose=props.onClose;
  const onNameChange=props.onNameChange;
  const onTagNameChange = props.onTagNameChange;
  const onTagValueChange = props.onTagValueChange;
  const onNewTag=props.onNewTag;
  const onDeleteTag=props.onDeleteTag;
  const onSubmit=props.onSubmit;

  // variables
  const combinedClass="update-notebook-modal-background "+(show?"shown":"hidden");

  // render
  return (
    <div className={ combinedClass}>
      <div className="update-notebook-modal">
        <IconButton icon={icons["cross"]} onClick={onClose}/>
        <div className="title">Notebook Update</div>
        <form className="update-notebook-form" onSubmit={(e)=>{onSubmit(index,e)}}>
          <input
            type="text"
            name="notebookName"
            onChange={onNameChange}
            value={notebook.name}
            placeholder="notebook name"
          />
          <TagListInput
            tags={notebook.tags}
            onNameChange={onTagNameChange}
            onValueChange={onTagValueChange}
            onNewTag={onNewTag}
            onDeleteTag={onDeleteTag}
          />
          <input type="submit" value="update"/>
        </form>
      </div>
    </div>
  );
}

// export
export default UpdateNotebookModal;
