import React, {useState, useEffect, useContext} from "react";
import {toast} from 'react-toastify';
import '../styles/labworkedit.css';
import {Link, useNavigate, useParams} from "react-router-dom";
import {AuthContext} from "../context";
import {getTopics} from '../functions/requests'

const LabWorkEdit = () => {
    const [labwork, setLabwork] = useState(null);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [topics, setTopics] = useState([]);
    const [newTopic, setNewTopic] = useState("");
    const [topicOptions, setTopicOptions] = useState([]);
    const {token} = useContext(AuthContext);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchLabwork() {
            try {
                const response = await fetch(`http://localhost:8080/lab-works/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                if (!response.ok) {
                    throw new Error("Не удалось получить данные лабораторной работы");
                }
                const data = await response.json();
                setLabwork(data);
                setName(data.name);
                setDescription(data.description);
                setTopics(data.topics);
            } catch (error) {
                console.error(error);
                toast.error(error.message || "Не удалось получить данные лабораторной работы");
            }
        }

        fetchLabwork();
    }, [id, token]);

    useEffect(() => {
        async function fetchTopics() {
            try {
                const data = await getTopics(token);
                setTopicOptions(data);
            } catch (error) {
                console.error("Возникла ошибка:", error);
                toast.error("Возникла ошибка");
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

    const handleSave = async () => {
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
                id,
                name,
                description,
                topics: topicData
            };

            const response = await fetch('http://localhost:8080/lab-works/update', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(labworkData),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Не удалось сохранить лабораторную работу");
            }

            toast.success("Лабораторная работа успешно сохранена");
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Не удалось сохранить лабораторную работу");
        }
    };

    function checkName(name) {
        console.log(name)
        return name.trim().length > 0;
    }

    function checkDescription(description) {
        return description.trim().length > 0;
    }

    function checkTopics(topics) {
        return topics.length > 0;
    }

    const handleDelete = async () => {
        try {
            const response = await fetch(`http://localhost:8080/lab-works/delete/${id}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Не удалось удалить лабораторную работу");
            }
            toast.success("Лабораторная работа успешно удалена")
            navigate('/labworks');
        } catch (error) {
            console.error("Ошибка при удалении:", error);
            toast.error(error.message || "Не удалось удалить лабораторную работу");
        }
    }

    return (
        <div className="page-container">
            <div>
                <Link to={{pathname: `/labworks/${id}`, state: {id}}}>
                    <div id="back-button" className="back-button">Назад</div>
                </Link>
            </div>
            <div className="labwork-edit-container">
                {!labwork ? (
                    <div>Loading...</div>
                ) : (
                    <form>
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
                            {topics.map((topic, index) => (
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
                                    <button type="button" className="delete-topic-button"
                                            onClick={() => handleRemoveTopic(index)}>X
                                    </button>
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
                            <button type="button" className="add-topic-button" onClick={handleAddTopic}>Добавить
                            </button>
                        </div>
                        <button type="button" className="save-button" onClick={handleSave}>Сохранить</button>
                        <button type="button" className="delete-lab-button" onClick={handleDelete}>Удалить лабораторную
                            работу
                        </button>
                    </form>
                )}
            </div>
        </div>
    );
};

export default LabWorkEdit;