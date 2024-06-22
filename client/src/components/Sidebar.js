

const Sidebar = (props) => {

    const noteList = props.notes.map((note)=> (
        <li key={note.id}
            className={`title ${
                note.id === props.currentNote.id ? "selected-note" : ""
            }`}
            onClick={() =>props.setCurrentNoteId(note.id)}
        >
            <span className="text-snippet">{note.content.split('\n')[0]}</span>
            <button className="delete-btn" onClick={() => props.deleteNote(note.id)}><i className="fa-solid fa-trash"></i></button>
        </li>
    ))

    return (
        <section className="sidebar">
            <div className="sidebar__header">
                <button className="sidebar__header-btn" onClick={props.newNote} >+</button>
            </div>
            <ul className="note-list" >
                {noteList}
            </ul>
        </section>
    )
}

export default Sidebar