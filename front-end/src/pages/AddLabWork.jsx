import React, {useState, useEffect, useContext} from 'react';
import {toast} from 'react-toastify';
import '../styles/addlabwork.css';
import {AuthContext} from "../context";
import {getTopics} from "../functions/requests";
import {useNavigate} from "react-router-dom";

const AddLabWork = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [topics, setTopics] = useState([]);
    const [newTopic, setNewTopic] = useState("");
    const [topicOptions, setTopicOptions] = useState([]);
    const {token} = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchTopics() {
            try {
                const data = await getTopics(token);
                setTopicOptions(data);
            } catch (error) {
                console.error(error);
                toast.error(error.message);
            }
        }

        fetchTopics();
    }, [token]);

    const handleAddTopic = () => {
        if (newTopic.trim() !== "") {
            const selectedTopic = topicOptions.find(topic => topic.name === newTopic);
            if (selectedTopic && !topics.find(topic => topic.name === newTopic)) {
                setTopics([...topics, {id: selectedTopic.id, name: newTopic}]);
                setNewTopic("");
            } else {
                console.error("Выбранная тема не найдена в списке topicOptions или уже добавлена");
                toast.error("Выбранная тема не найдена в списке topicOptions или уже добавлена");
            }
        }
    };

    const handleRemoveTopic = (index) => {
        const updatedTopics = [...topics];
        updatedTopics.splice(index, 1);
        setTopics(updatedTopics);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!checkName(name)) {
            toast.error("Необходимо заполнить название лабораторной работы");
            return;
        }

        if (!checkDescription(description)) {
            toast.error("Необходимо заполнить описание лабораторной работы");
            return;
        }

        if (!checkTopics(topics)) {
            toast.error("Необходимо добавить хотя бы одну тему");
            return;
        }

        try {
            const topicData = topics.map(topic => ({id: topic.id, name: topic.name}));
            const labworkData = {
                name,
                description,
                topics: topicData
            };

            const response = await fetch('http://localhost:8080/lab-works/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(labworkData),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Не удалось добавить лабораторную работу");
            }
            toast.success('Лабораторная работа успешно добавлена');
            navigate('/labworks');
        } catch (error) {
            console.error(error);
            toast.error(error.message);
        }
    };

    function checkName(name) {
        return name.trim().length > 0;
    }

    function checkDescription(description) {
        return description.trim().length > 0;
    }

    function checkTopics(topics) {
        return topics.length > 0;
    }

    return (
        <div className="page-container">
            <div className="add-labwork-container">
                <form onSubmit={handleSubmit}>
                    <label>Название:</label>
                    <input
                        id="labwork-name"
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                    <label>Описание:</label>
                    <textarea
                        id="labwork-description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <label>Темы:</label>
                    <ul>
                        {topics && topics.map((topic, index) => (
                            <li key={index}>
                                <input
                                    className="added-topic"
                                    type="text"
                                    value={topic.name}
                                    onChange={(e) => {
                                        const updatedTopics = [...topics];
                                        updatedTopics[index].name = e.target.value;
                                        setTopics(updatedTopics);
                                    }}
                                    readOnly
                                />
                                <button className="delete-topic-button" type="button" onClick={() => handleRemoveTopic(index)}>X</button>
                            </li>
                        ))}
                    </ul>
                    <div className="add-topic-div">
                        <select value={newTopic} onChange={(e) => setNewTopic(e.target.value)}>
                            <option value="">Выбрать тему</option>
                            {topicOptions.filter(option => !topics.find(topic => topic.name === option.name)).map((option, index) => (
                                <option key={index} value={option.name}>{option.name}</option>
                            ))}
                        </select>
                        <button className="add-topic-button" type="button" onClick={handleAddTopic}>Добавить</button>
                    </div>
                    <button type="submit">Добавить лабораторную работу</button>
                </form>
            </div>
        </div>
    );
};

export default AddLabWork;