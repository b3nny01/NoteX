// css
import "./NewNoteModal.css";

// data
import icons from "../../data/icons";

// components
import IconButton from "../../components/IconButton";
import TagListInput from "../../components/TagListInput";


function NewNoteModal(props) {
  // parameters
  const show=props.show;
  const newNote=props.newNote;
  const onClose=props.onClose;
  const onNameChange=props.onNameChange;
  const onFileChange=props.onFileChange;
  const onTagNameChange = props.onTagNameChange;
  const onTagValueChange = props.onTagValueChange;
  const onNewTag=props.onNewTag;
  const onDeleteTag=props.onDeleteTag;
  const onSubmit=props.onSubmit;
  const inputFileRef=props.inputFileRef;

  // variables
  const combinedClass="new-note-modal-background "+(show?"shown":"hidden");

  // render
  return (
    <div className={ combinedClass}>
      <div className="new-note-modal">
        <IconButton icon={icons["cross"]} onClick={onClose}/>
        <div className="title">Note Creation</div>
        <form className="new-note-form" onSubmit={onSubmit}>
          <input
            type="text"
            name="noteName"
            onChange={onNameChange}
            value={newNote.name}
            placeholder="note name"
          />
          <input type="file" name="noteFile" onChange={onFileChange} ref={inputFileRef}/>
          <TagListInput
            tags={newNote.tags}
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
export default NewNoteModal;
