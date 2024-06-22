import axios from "axios";

const baseURL = process.env.REACT_APP_API_BASE_URL ;


export const createNewNote = async (props) => {
    try {

        const response = await axios.post(`${baseURL}/api/notes/new`, props.newNote, {
          headers : {'Authorization' : props.token}
        });

        return response;
      } catch (error) {
        console.error(error);
      }
}

export const fetchAllNotes = async (props) => {
  try {
    const response = await axios.get(`${baseURL}/api/notes/getAllNotesByAuthor?author=${props.email}`, {
      headers : { 'Authorization' : props.token }
    });

    return response ;
  } catch (e) {
    console.log(e.message);
  }
}

export const updateNewNote = async (props) => {
  try {
    const response = await axios.post(`${baseURL}/api/notes/update`, props.updatedNote ,{
        headers : { 'Authorization' : props.token }
      });

    return response ;

  } catch (err) {
    console.log(err.message);
  }
}

export const deleteNoteApi = async (props) => {
  try {
    const response = await axios.get(`${baseURL}/api/notes/delete?blogId=${props.noteId}`, {
      headers : { 'Authorization' : props.token }
    });
    return response;
  } catch (err) {
    console.log(err.message);
  }
}