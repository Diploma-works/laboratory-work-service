import React, {useState, useEffect, useContext} from 'react';
import '../styles/addtopic.css';
import {AuthContext} from "../context";
import {getTopics} from "../functions/requests";
import {toast} from "react-toastify";

const EditTopics = () => {
    const [topics, setTopics] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [newTopic, setNewTopic] = useState('');
    const [modalOpen, setModalOpen] = useState(false);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        async function fetchTopics() {
            try {
                const data = await getTopics(token);
                setTopics(data);
            } catch (error) {
                console.error(error);
                toast.error(error.message);
            }
        }

        fetchTopics();
    }, [token]);

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
    };

    const filteredTopics = searchTerm !== "" ? topics.filter(topic => {
        return topic.name.toLowerCase().includes(searchTerm.toLowerCase());
    }) : topics;

    const handleAddTopic = async () => {
        try {
            const data = {
                name: newTopic
            };
            const response = await fetch('http://localhost:8080/topics/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(data),
            });
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Тема с таким названием уже существует");
            }
            const newTopicData = await response.json();
            setTopics([...topics, newTopicData]);
            setNewTopic('');
            setModalOpen(false);
            toast.success("Тема успешно добавлена")
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Тема с таким названием уже существует");

        }
    };

    const handleRemoveTopic = async (index) => {
        const updatedTopics = [...topics];
        try {
            const deletingTopic = topics[index];
            const deletingTopicId = deletingTopic.id;
            const response = await fetch(`http://localhost:8080/topics/delete/${deletingTopicId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            if (!response.ok) {
                throw new Error("Тема используется в лабораторной работе");
            }
            updatedTopics.splice(index, 1);
            setTopics(updatedTopics);
            toast.success("Тема успешно удалена")
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Тема используется в лабораторной работе");
        }
    };

    const openModal = () => {
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
    };

    return (
        <div className="page-container">
            <div className="add-topic-container">
                <div className="top-container">
                    <h1>Темы заданий</h1>
                    <div onClick={openModal} className="add-topic-button">Добавить новую тему</div>
                    {modalOpen && (
                        <div className="modal">
                            <div className="modal-content">
                                <span className="close-button" onClick={closeModal}>&times;</span>
                                <h2>Добавить новую тему</h2>
                                <input
                                    type="text"
                                    placeholder="Введите название темы..."
                                    value={newTopic}
                                    onChange={(e) => setNewTopic(e.target.value)}
                                    className="new-topic-input"
                                />
                                <button onClick={handleAddTopic} className="add-button">Добавить</button>
                            </div>
                        </div>
                    )}
                </div>
                <div>
                    <input
                        type="text"
                        placeholder="Поиск темы..."
                        value={searchTerm}
                        onChange={handleSearch}
                        className="search-input"
                    />
                </div>
                <ul className="topic-list">
                    {filteredTopics.map((topic, index) => (
                        <li key={index} className="topic-item">
                            <input
                                className="added-topic"
                                type="text"
                                value={topic.name}
                                onChange={(e) => {
                                    const updatedTopics = [...topics];
                                    updatedTopics[index].name = e.target.value;
                                    setTopics(updatedTopics);
                                }}
                                disabled
                            />
                            <button type="button" onClick={() => handleRemoveTopic(index)} className="remove-button">X
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default EditTopics;