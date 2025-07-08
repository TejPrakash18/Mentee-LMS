import api from './api';

const username = localStorage.getItem("username"); // or use a prop

export const getNote = async (questionId) => {
  try {
    const res = await api.get(`/notes/${questionId}?username=${username}`);
    return res.data;
  } catch (err) {
    if (err.response?.status === 204 || err.response?.status === 404) return null;
    throw err;
  }
};

export const saveNote = async (questionId, content) => {
  try {
    const res = await api.post(`/notes/${questionId}?username=${username}`, content, {
      headers: { 'Content-Type': 'text/plain' },
    });
    return res.data;
  } catch (err) {
    throw err;
  }
};
