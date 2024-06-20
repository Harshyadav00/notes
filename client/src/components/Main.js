import Editor from "./Editor"
import Split from 'react-split'
import Sidebar from './Sidebar';
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useAuth } from "../actions/AuthProvider";
import axios from 'axios';
import { createNewNote, deleteNoteApi, fetchAllNotes, updateNewNote } from "../service/post";

const baseURL = process.env.REACT_APP_API_BASE_URL;

const Main = () => {
  const { user, token } = useAuth();
  const [notes, setNotes] = useState([]);
  const [currentNoteId, setCurrentNoteId] = useState('');
  const [text, setText] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate('/signin');
    }
  }, [user]);

  useEffect(() => {
    fetchNotes();
    findCurrentNote();
  }, []);

  const fetchNotes = async () => {
    try {

      const data = {
        email: user.email,
        token
      }

      const response = await fetchAllNotes(data);

      setNotes(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const createNote = async () => {
    try {
      const newNote = {
        content: `# Enter title here \n\n`,
      };
      const data = {
        newNote,
        token
      }
      const response = await createNewNote(data);
      const newNotes = ((prevNotes) => [...prevNotes, response.data]);
      setNotes(newNotes);
      setCurrentNote(response.data.id);
    } catch (error) {
      console.error(error);
    }
  };


  const updateText = (text) => {
    setText(text);
  }

  const updateNote = async () => {
    try {

      const updatedNote = {
        id: currentNoteId,
        content: text,
      }

      const data = {
        updatedNote,
        token,
      }

      await updateNewNote(data);

      await fetchNotes();

    } catch (error) {
      console.error(error);
    }
  };

  const deleteNote = async (noteId) => {
    try {
      const data = {
        noteId,
        token
      }
      console.log(token);
      await deleteNoteApi(data);
      fetchNotes();
    } catch (error) {
      console.error(error);
    }
  };

  function findCurrentNote() {
    return notes.find(note => note.id === currentNoteId) || notes[0];
  }

  function setCurrentNote(id) {
    setCurrentNoteId(id);
    const currentNote = notes.find(note => note.id === id) || notes[0];
    // console.log("Hello -> ", currentNote);
    setText(currentNote.content);
  }


  return (
    <main>
      {
        notes.length > 0 ?
          <Split
            sizes={[15, 85]}
            direction="horizontal"
            className='split'
          >
            <Sidebar
              notes={notes}
              setCurrentNoteId={setCurrentNote}
              currentNote={findCurrentNote()}
              newNote={createNote}
              deleteNote={deleteNote}
            />
            {currentNoteId && notes.length > 0 &&
              <Editor
                save={updateNote}
                text={text}
                updateText={updateText}
                currentNote={findCurrentNote()}
              />
            }
          </Split>
          :
          <div className="no-notes">
            <h1>You have no notes</h1>
            <button
              className="first-note"
              onClick={createNote}
            >
              Create one now
            </button>
          </div>
      }
    </main>
  )

}

export default Main;