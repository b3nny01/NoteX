// css
import "./UpdateNoteModal.css";

// data
import icons from "../../data/icons";

// components
import IconButton from "../../components/IconButton";
import TagListInput from "../../components/TagListInput";

function UpdateNoteModal(props) {
  // parameters
  const show = props.show;
  const index = props.index;
  const note = props.note;
  const onClose = props.onClose;
  const onNameChange = props.onNameChange;
  const onFileChange=props.onFileChange
  const onTagNameChange = props.onTagNameChange;
  const onTagValueChange = props.onTagValueChange;
  const onNewTag = props.onNewTag;
  const onDeleteTag = props.onDeleteTag;
  const onSubmit = props.onSubmit;
  const inputFileRef=props.inputFileRef;

  // variables
  const combinedClass =
    "update-note-modal-background " + (show ? "shown" : "hidden");

  // render
  return (
    <div className={combinedClass}>
      <div className="update-note-modal">
        <IconButton icon={icons["cross"]} onClick={onClose} />
        <div className="title">note Update</div>
        <form
          className="update-note-form"
          onSubmit={(e) => {
            onSubmit(index, e);
          }}
        >
          <input
            type="text"
            name="noteName"
            onChange={onNameChange}
            value={note.name}
            placeholder="note name"
          />
          <input
            type="file"
            name="noteFile"
            onChange={onFileChange}
            ref={inputFileRef}
          />
          <TagListInput
            tags={note.tags}
            onNameChange={onTagNameChange}
            onValueChange={onTagValueChange}
            onNewTag={onNewTag}
            onDeleteTag={onDeleteTag}
          />
          <input type="submit" value="update" />
        </form>
      </div>
    </div>
  );
}

// export
export default UpdateNoteModal;
