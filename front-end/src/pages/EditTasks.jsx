import React, {useState, useEffect, useContext} from 'react';
import '../styles/addtask.css';
import {AuthContext} from "../context";
import {getTopics} from "../functions/requests";
import {toast} from "react-toastify";

const AddTask = () => {
    const [topics, setTopics] = useState([]);
    const [selectedTopic, setSelectedTopic] = useState('');
    const [tasks, setTasks] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);
    const [newTask, setNewTask] = useState('');
    const [searchTerm, setSearchTerm] = useState('');
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

    useEffect(() => {
        async function fetchTasks() {
            try {
                const response = await fetch(`http://localhost:8080/tasks/get-tasks-by-topic-id/${selectedTopic}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                if (!response.ok) {
                    throw new Error("Не удалось загрузить задачи");
                }
                const data = await response.json();
                setTasks(data);
            } catch (error) {
                console.error(error);
                toast.error(error.message || "Не удалось загрузить задачи");
            }
        }

        if (selectedTopic) {
            fetchTasks();
        }
    }, [selectedTopic, token]);

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
    };

    const filteredTasks = searchTerm !== "" ? tasks.filter(task => {
        return task.description.toLowerCase().includes(searchTerm.toLowerCase());
    }) : tasks;

    const handleTopicChange = (e) => {
        setSelectedTopic(e.target.value);
        if (e.target.value.trim() === '') {
            setTasks([]);
        }
    };

    const handleRemoveTask = async (index) => {
        const updatedTasks = [...tasks];
        try {
            const deletingTask = tasks[index];
            const deletingTaskId = deletingTask.id;
            const response = await fetch(`http://localhost:8080/tasks/delete/${deletingTaskId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Задание используется в сгенерированном варианте задания");
            }
            updatedTasks.splice(index, 1);
            setTasks(updatedTasks);
            toast.success("Задание успешно удалено");
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Задание используется в сгенерированном варианте задания");
        }
    };

    const handleAddTask = async () => {
        try {
            const data = {
                description: newTask,
                topic_id: selectedTopic
            };
            const response = await fetch('http://localhost:8080/tasks/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(data),
            });
            if (!response.ok) {
                throw new Error("Не удалось добавить задачу");
            }
            const newTaskData = await response.json();
            setTasks([...tasks, newTaskData]);
            setNewTask('');
            setModalOpen(false);
            toast.success("Задание успешно добавлено")
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Не удалось добавить задание");
        }
    };

    const openModal = () => {
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
    };

    return (
        <div className="add-task-container">
            <div className="top-container">
                <h1>Добавить задание</h1>
                {selectedTopic && (
                    <button onClick={openModal} className="add-task-button">Добавить новое задание</button>
                )}
                {modalOpen && (
                    <div className="modal">
                        <div className="modal-content">
                            <span className="close-button" onClick={closeModal}>&times;</span>
                            <h2>Добавить новое задание</h2>
                            <input
                                type="text"
                                placeholder="Введите название задания..."
                                value={newTask}
                                onChange={(e) => setNewTask(e.target.value)}
                                className="new-task-input"
                            />
                            <button onClick={handleAddTask} className="add-button">Добавить</button>
                        </div>
                    </div>
                )}
            </div>
            <div>
                <input
                    type="text"
                    placeholder="Поиск задания..."
                    value={searchTerm}
                    onChange={handleSearch}
                    className="search-input"
                />
            </div>
            Тема:
            <select value={selectedTopic} onChange={handleTopicChange}>
                <option value="">Выберите тему</option>
                {topics.map(topic => (
                    <option key={topic.id} value={topic.id}>{topic.name}</option>
                ))}
            </select>
            <ul className="task-list">
                {filteredTasks.map((task, index) => (
                    <li key={index} className="task-item">
                        <input
                            className="added-task"
                            type="text"
                            value={task.description}
                            onChange={(e) => {
                                const updatedTasks = [...tasks];
                                updatedTasks[index].name = e.target.value;
                                setTasks(updatedTasks);
                            }}
                            disabled
                        />
                        <button type="button" onClick={() => handleRemoveTask(index)} className="remove-button">X
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AddTask;