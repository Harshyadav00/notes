import React, { useState } from "react";
import ReactMde from "react-mde";
import * as Showdown from "showdown";
import "react-mde/lib/styles/css/react-mde-all.css";

const converter = new Showdown.Converter({
  tables: true,
  simplifiedAutoLink: true,
  strikethrough: true,
  tasklists: true
});

export default function Editor(props) {
  const [selectedTab, setSelectedTab] = useState("write");

  return (
    <div className="container">
      <button onClick={props.save} >Save</button>
      <ReactMde
        value={props.text}
        onChange={props.updateText}
        selectedTab={selectedTab}
        onTabChange={setSelectedTab}
        generateMarkdownPreview={markdown =>
          Promise.resolve(converter.makeHtml(markdown))
        }
        minEditorHeight={80}
        minPreviewHeight={80}
        heightUnits="vh"
      />
    </div>
  );
}