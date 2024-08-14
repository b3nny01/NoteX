// css
import "./VirtualDeskFeature.css";

// hooks
import { useEffect, useState } from "react";

// data
import icons from "../../data/icons";

// components
import NotebookSubDiv from "./NotebookSubDiv";
import NewNotebookModal from "./NewNotebookModal";
import APIs from "../../data/api";
import APIParameter from "../../utils/APIParameter";
import UpdateNotebookModal from "./UpdateNotebookModal";


function VirtualDeskFeature(props) {
  // state
  const [notebooks, setNotebooks] = useState([]);
  const [newNotebook, setNewNotebook] = useState({
    name: "",
    score: -1,
    tags: [],
    notes: [],
  });

  const [toUpdateIndex,setToUpdateIndex]=useState(-1);
  const [showNewNotebookModal, setShowNewNotebookModal] = useState(false);
  const [showUpdateNotebookModal, setShowUpdateNotebookModal]=useState(false);
  // params
  const onSelectNotebook = props.onSelectNotebook;

  // methods
  const handleUpdateNotebooks = () => {

    APIs.retrieveNotebooks.fetch([]).then((res) => {
      res.json().then((apiRes) => {
        if (apiRes.ok) {
          setNotebooks(apiRes.obj);
        } else {
          alert(apiRes.msg);
        }
      });
    });
  };

  const handleCreateNewNotebook = (e) => {
    e.preventDefault();
    const apiParams = [
      new APIParameter("notebookName", newNotebook.name),
      new APIParameter("tags", JSON.stringify(newNotebook.tags))
    ]
    APIs.createNotebook.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
        }
        handleUpdateNotebooks();
        handleCloseNewNotebookModal();
      });
    });
  };
  const handleUpdateNotebook=(i,e)=>{
    e.preventDefault();
    const apiParams = [
      new APIParameter("oldNotebookName",notebooks[i].name),
      new APIParameter("newNotebookName", newNotebook.name),
      new APIParameter("newTags", JSON.stringify(newNotebook.tags))
    ]
    APIs.updateNotebook.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
        }
        handleUpdateNotebooks();
        handleCloseUpdateNotebookModal();
      });
    });
  }

  const handleDeleteNotebook = (i) => {
    const apiParams = [
      new APIParameter("notebookName", notebooks[i].name)
    ];

    APIs.deleteNotebook.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
        }
        handleUpdateNotebooks();
      });
    });
  }


  const handleOpenNewNotebookModal = () => {
    setShowNewNotebookModal(true);
  };

  const handleCloseNewNotebookModal = () => {
    setShowNewNotebookModal(false);
    setNewNotebook({
      name: "",
      score: -1,
      tags: [],
      notes: [],
    });
  };

  const handleOpenUpdateNotebookModal = (i)=>{
    let newNewNotebook={...notebooks[i]};
    setToUpdateIndex(i);
    setNewNotebook(newNewNotebook);
    setShowUpdateNotebookModal(true);
  }

  const handleCloseUpdateNotebookModal = ()=>{
    setShowUpdateNotebookModal(false);
    setToUpdateIndex(-1);
    setNewNotebook({
      name: "",
      score: -1,
      tags: [],
      notes: [],
    });
  }

  const handleChangeNewNotebookName = (e) => {
    let newNewNotebook = { ...newNotebook, name: e.target.value };
    setNewNotebook(newNewNotebook);
  };

  const handleNewNotebookTagNameChange = (i, e) => {
    let newNewNotebook = { ...newNotebook };
    newNewNotebook.tags[i].name = e.target.value;
    setNewNotebook(newNewNotebook);
  };

  const handlenewNotebookTagValueChange = (i, e) => {
    let newNewNotebook = { ...newNotebook };
    newNewNotebook.tags[i].value = e.target.value;
    setNewNotebook(newNewNotebook);
  };

  const handleNewNotebookNewTag = (e) => {
    let newNewNotebook = { ...newNotebook };
    newNewNotebook.tags.push({ name: "", value: "" });
    setNewNotebook(newNewNotebook);
  };

  const handleNewNotebookDeleteTag = (e) => {
    let newNewNotebook = { ...newNotebook };
    newNewNotebook.tags.pop();
    setNewNotebook(newNewNotebook);
  };

  // effects
  useEffect(handleUpdateNotebooks, []); //init

  // variables
  const notebookSubDivs = [];

  // logic
  for (let i = 0; i < notebooks.length; i++) {
    notebookSubDivs[i] = (
      <NotebookSubDiv
        notebook={notebooks[i]}
        key={notebooks[i].name}
        index={i}
        onSelectNotebook={onSelectNotebook}
        onUpdateNotebook={handleOpenUpdateNotebookModal}
        onDeleteNotebook={handleDeleteNotebook}
      />
    );
  }
  notebookSubDivs.push(
    <div className="notebook-sub-div" key="newNotebook">
      <div className="icon-name-div">
        <img
          className="icon-img"
          src={icons["add"].src}
          onClick={handleOpenNewNotebookModal}
        />
        <div className="name">new notebook</div>
      </div>
      <div className="actions"></div>
    </div>
  );

  // render
  return (
    <div className="virtual-desk-feature">
      {notebookSubDivs}
      <NewNotebookModal
        show={showNewNotebookModal}
        newNotebook={newNotebook}
        onClose={handleCloseNewNotebookModal}
        onNameChange={handleChangeNewNotebookName}
        onTagNameChange={handleNewNotebookTagNameChange}
        onTagValueChange={handlenewNotebookTagValueChange}
        onNewTag={handleNewNotebookNewTag}
        onDeleteTag={handleNewNotebookDeleteTag}
        onSubmit={handleCreateNewNotebook}
      />
      <UpdateNotebookModal
        show={showUpdateNotebookModal}
        index={toUpdateIndex}
        notebook={newNotebook}
        onClose={handleCloseUpdateNotebookModal}
        onNameChange={handleChangeNewNotebookName}
        onTagNameChange={handleNewNotebookTagNameChange}
        onTagValueChange={handlenewNotebookTagValueChange}
        onNewTag={handleNewNotebookNewTag}
        onDeleteTag={handleNewNotebookDeleteTag}
        onSubmit={handleUpdateNotebook}
      />
    </div>
  );
}

// export
export default VirtualDeskFeature;
