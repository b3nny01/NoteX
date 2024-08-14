// css
import "./NotebookFeature.css";

// hooks
import { useEffect, useState, useRef } from "react";

// data
import icons from "../../data/icons";

// components
import NoteSubDiv from "./NoteSubDiv";
import NewNoteModal from "./NewNoteModal";
import UpdateNoteModal from "./UpdateNoteModal";
import  APIs from "../../data/api";
import APIParameter from "../../utils/APIParameter"

function NotebookFeature(props) {
  // state
  const [notes, setNotes] = useState([]);
  const [newNote, setNewNote] = useState({
    name: "",
    score: -1,
    tags: [],
    file: null,
    reviews: [],
  });

  const [toUpdateIndex,setToUpdateIndex]=useState(-1);
  const [showNewNoteModal, setShowNewNoteModal] = useState(false);
  const [showUpdateNoteModal, setShowUpdateNoteModal]=useState(false);

  const inputFileRef = useRef();

  // params
  const notebookName = props.notebookName;

  // methods
  const handleUpdateNotes = () => {

    const apiParams = [
      new APIParameter("notebookName", notebookName),
    ];

    APIs.retrieveNotes.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (apiRes.ok) {
          setNotes(apiRes.obj);
        } else {
          alert(apiRes.msg)
        }
      });
    });
  };

  const handleCreateNewNote = (e) => {
    e.preventDefault();
    const apiParams = [
      new APIParameter("notebookName", notebookName),
      new APIParameter("noteName", newNote.name),
      new APIParameter("tags", JSON.stringify(newNote.tags)),
      new APIParameter("file", newNote.file)];

      APIs.createNote.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
        }
        handleUpdateNotes();
        handleCloseNewNoteModal();
      });
    });
  };

  const handleUpdateNote=(i,e)=>{
    e.preventDefault();
    const apiParams = [
      new APIParameter("notebookName",notes[i].notebook),
      new APIParameter("oldNoteName",notes[i].name),
      new APIParameter("newNoteName", newNote.name),
      new APIParameter("newTags", JSON.stringify(newNote.tags))
    ]
    if(newNote.file!=null){
      apiParams.push(new APIParameter("newFile", newNote.file));
    }
    
    APIs.updateNote.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
        }
        handleUpdateNotes();
        handleCloseUpdateNoteModal();
      });
    });
  }

  const handleDeleteNote = (i) => {
    const apiParams = [
      new APIParameter("notebookName", notebookName),
      new APIParameter("noteName", notes[i].name)
    ];

    APIs.deleteNote.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
        }
        handleUpdateNotes();
      });
    });
  };

  const handleOpenNewNoteModal = () => {
    setShowNewNoteModal(true);
  };

  const handleCloseNewNoteModal = () => {
    setShowNewNoteModal(false);
    setNewNote({
      name: "",
      score: -1,
      file: null,
      tags: [],
      reviews: [],
    });
    inputFileRef.current.value = null;
  };

  const handleOpenUpdateNoteModal = (i)=>{
    let newNewNote={...notes[i]};
    setToUpdateIndex(i);
    setNewNote(newNewNote);
    setShowUpdateNoteModal(true);
  }

  const handleCloseUpdateNoteModal = ()=>{
    setShowUpdateNoteModal(false);
    setToUpdateIndex(-1);
    setNewNote({
      name: "",
      score: -1,
      file: null,
      tags: [],
      reviews: [],
    });
    inputFileRef.current.value = null;
  }

  

 

  const handleChangeNewNoteName = (e) => {
    let newNewNote = { ...newNote, name: e.target.value };
    setNewNote(newNewNote);
  };

  const handleChangeNewNoteFile = (e) => {
    let newNewNote = { ...newNote, file: e.target.files[0] };
    setNewNote(newNewNote);
  };

  const handleChangeNewNoteTagName = (i, e) => {
    let newNewNote = { ...newNote };
    newNewNote.tags[i].name = e.target.value;
    setNewNote(newNewNote);
  };

  const handleChangeNewNoteTagValue = (i, e) => {
    let newNewNote = { ...newNote };
    newNewNote.tags[i].value = e.target.value;
    setNewNote(newNewNote);
  };

  const handleNewNoteNewTag = (e) => {
    let newNewNote = { ...newNote };
    newNewNote.tags.push({ name: "", value: "" });
    setNewNote(newNewNote);
  };

  const handleNewNoteDeleteTag = (e) => {
    let newNewNote = { ...newNote };
    newNewNote.tags.pop();
    setNewNote(newNewNote);
  };

  // effects
  useEffect(handleUpdateNotes, []); //init

  // variables
  const noteSubDivs = [];

  // logic
  for (let i = 0; i < notes.length; i++) {
    noteSubDivs[i] = (
      <NoteSubDiv
        note={notes[i]}
        key={notes[i].name}
        index={i}
        onUpdateNote={handleOpenUpdateNoteModal}
        onDeleteNote={handleDeleteNote}
      />
    );
  }
  noteSubDivs.push(
    <div className="note-sub-div" key="newNote">
      <div className="icon-name-div">
        <img
          className="icon-img"
          src={icons["add"].src}
          onClick={handleOpenNewNoteModal}
        />
        <div className="name">new note</div>
      </div>
      <div className="actions"></div>
    </div>
  );

  // render
  return (
    <div className="notebook-feature">
      {noteSubDivs}
      <NewNoteModal
        show={showNewNoteModal}
        onClose={handleCloseNewNoteModal}
        newNote={newNote}
        onNameChange={handleChangeNewNoteName}
        onFileChange={handleChangeNewNoteFile}
        onTagNameChange={handleChangeNewNoteTagName}
        onTagValueChange={handleChangeNewNoteTagValue}
        onNewTag={handleNewNoteNewTag}
        onDeleteTag={handleNewNoteDeleteTag}
        onSubmit={handleCreateNewNote}
        inputFileRef={inputFileRef}
      />
      <UpdateNoteModal
        show={showUpdateNoteModal}
        index={toUpdateIndex}
        note={newNote}
        onClose={handleCloseUpdateNoteModal}
        onNameChange={handleChangeNewNoteName}
        onFileChange={handleChangeNewNoteFile}
        onTagNameChange={handleChangeNewNoteTagName}
        onTagValueChange={handleChangeNewNoteTagValue}
        onNewTag={handleNewNoteNewTag}
        onDeleteTag={handleNewNoteDeleteTag}
        onSubmit={handleUpdateNote}
        inputFileRef={inputFileRef}
        />
        
    </div>
  );
}

// export
export default NotebookFeature;
