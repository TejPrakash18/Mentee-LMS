import React, { useEffect, useState, useRef } from "react";
import MDEditor from "@uiw/react-md-editor";
import { motion, AnimatePresence } from "framer-motion";
import { ImCross } from "react-icons/im";
import { CiStickyNote } from "react-icons/ci";
import { getNote, saveNote } from "../services/noteService";
import "@uiw/react-markdown-preview/markdown.css";

const NotesEditor = ({ questionId }) => {
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState(true);
  const [note, setNote] = useState("");
  const [isSaving, setIsSaving] = useState(false);
  const [savedMessage, setSavedMessage] = useState("");
  const [previewMode, setPreviewMode] = useState("edit"); // 'edit' | 'preview'
  const prevNoteRef = useRef("");

  // Fetch note
  useEffect(() => {
    if (!visible || !questionId) return;
    const fetchNote = async () => {
      setLoading(true);
      try {
        const data = await getNote(questionId);
        setNote(data?.content || "");
        prevNoteRef.current = data?.content || "";
      } catch (err) {
        console.error("Error fetching note:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchNote();
  }, [visible, questionId]);

  // Auto-save every 3 seconds
  useEffect(() => {
    if (!visible || !questionId) return;
    const interval = setInterval(async () => {
      if (note === prevNoteRef.current) return;
      setIsSaving(true);
      try {
        await saveNote(questionId, note);
        prevNoteRef.current = note;
        setSavedMessage(" ");
      } catch (err) {
        console.error("Auto-save failed:", err);
        setSavedMessage("Error saving");
      } finally {
        setIsSaving(false);
        setTimeout(() => setSavedMessage(""), 3000);
      }
    }, 3000);

    return () => clearInterval(interval);
  }, [note, visible, questionId]);

  return (
    <div>
      {/* Open Notes Button */}
      <button
        onClick={() => setVisible(true)}
        className="flex items-center gap-2 px-3 py-1.5 rounded-md bg-[#1A1A1A] text-white shadow-xl hover:scale-105 transition duration-200 z-50 group"
        title="Open Notes"
      >
        <CiStickyNote size={20} className="text-white" />
        <span className="absolute -top-2 -right-2 text-[10px] bg-white text-black px-2 py-0.5 rounded-full shadow-sm hidden group-hover:block">
          Notes
        </span>
      </button>

      {/* Notes Panel */}
      <AnimatePresence>
        {visible && (
          <motion.div
            className="fixed top-0 left-0 h-full w-full md:w-[600px] bg-[#1e1e1e] shadow-2xl z-50 border-r border-gray-700 flex flex-col"
            initial={{ x: "-100%" }}
            animate={{ x: 0 }}
            exit={{ x: "-100%" }}
            transition={{ duration: 0.35 }}
            data-color-mode="dark"
            style={{ maxHeight: "100vh" }}
          >
            {/* Header */}
            <div className="sticky top-0 z-10 flex justify-between items-center px-4 py-3 bg-[#1e1e1e] border-b border-gray-700">
              <h2 className="text-white text-sm font-semibold tracking-wide">
                My Notes
              </h2>
              <div className="flex items-center gap-4 text-xs text-gray-400">
                {isSaving ? (
                  <span className="text-yellow-400 animate-pulse">
                    Saving...
                  </span>
                ) : savedMessage ? (
                  <span className="text-green-400">{savedMessage}</span>
                ) : (
                  <span>Idle</span>
                )}
                <button
                  onClick={() => setVisible(false)}
                  className="text-gray-400 hover:text-red-500 transition"
                >
                  <ImCross size={14} />
                </button>
              </div>
            </div>

            {/* Editor / Preview Container */}
            <div className="flex-grow px-4 pb-6 overflow-auto min-h-0">
              {loading ? (
                <div className="text-center text-gray-400 mt-10">
                  Loading note...
                </div>
              ) : (
                <div className="relative">
                  {/* Placeholder */}
                  {note.trim() === "" && previewMode === "edit" && (
                    <div className="absolute top-[35px] left-[12px] text-gray-400 pointer-events-none z-10 text-base">
                      Type here... (Markdown enabled)
                    </div>
                  )}

                  <MDEditor
                    value={note}
                    onChange={setNote}
                    preview={previewMode}
                    onPreviewChange={(mode) => {
                      if (mode === "live") {
                        setPreviewMode("edit");
                      } else {
                        setPreviewMode(mode);
                      }
                    }}
                    style={{
                      backgroundColor: "#1e1e1e",
                      color: "white",
                      border: "none",
                      fontSize: "14px",
                      height: "100%",
                      minHeight: "calc(100vh - 100px)",
                      overflow: "auto",
                    }}
                  />
                </div>
              )}
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default NotesEditor;
