import React, {useState, useEffect, useContext} from 'react';
import {AuthContext} from "../context";
import '../styles/answerlist.css';
import {format} from "date-fns";
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AnswerList = ({labWorkId, taskId, variant}) => {
    const [answers, setAnswers] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [file, setFile] = useState(null);
    const [description, setDescription] = useState("");
    const {token, role, user} = useContext(AuthContext);

    useEffect(() => {
        async function fetchAnswerList() {
            try {
                let response;
                if (role && role === 'ADMIN') {
                    if (!labWorkId) {
                        response = await fetch(`http://localhost:8080/answers`, {
                            headers: {
                                Authorization: `Bearer ${token}`
                            }
                        });
                    } else {
                        response = await fetch(`http://localhost:8080/answers/${labWorkId}`, {
                            headers: {
                                Authorization: `Bearer ${token}`
                            }
                        });
                    }
                } else {
                    response = await fetch(`http://localhost:8080/answers/${labWorkId}`, {
                        method: 'POST',
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({user})
                    });
                }
                if (!response.ok) {
                    throw new Error('Не удалось загрузить лабораторную работу');
                }
                const data = await response.json();
                setAnswers(data);
            } catch (error) {
                console.error(error);
                toast.error(error.message || 'Не удалось загрузить лабораторную работу');
            }
        }

        if (role) {
            fetchAnswerList();
        }
    }, [labWorkId, role, token, user]);

    const handleAddAnswer = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setFile(null);
        setDescription("");
    };

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleSubmit = async () => {
        const dateTime = new Date();
        const formattedDateTime = format(dateTime, "yyyy-MM-dd'T'HH:mm:ss");

        if (!checkAnswerData(file, description)) {
            toast.error("Необходимо добавить файл или описание");
            return;
        }

        const formData = new FormData();
        formData.append('description', description);
        formData.append('dateTime', formattedDateTime);
        formData.append('variant', variant);
        formData.append('taskId', taskId);
        formData.append('username', user);

        if (file) {
            formData.append('file', file);
            formData.append('filename', file.name);
        }

        try {
            const response = await fetch(`http://localhost:8080/answers/create`, {
                method: 'POST',
                body: formData,
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error("Не удалось отправить ответ");
            }

            const data = await response.json();
            setAnswers(prevAnswers => [...prevAnswers, data]);

            setIsModalOpen(false);
            setFile(null);
            setDescription("");
            toast.success("Ответ успешно добавлен");
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Не удалось отправить файл");
        }
    };

    function checkAnswerData(file, description) {
        return file || description;
    }

    const handleDownloadFile = async (answerId) => {
        try {
            const response = await fetch(`http://localhost:8080/answers/download-file`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({answerId}),
                responseType: 'blob'
            });

            if (!response.ok) {
                throw new Error("Не удалось скачать файл");
            }
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            const contentDispositionHeader = response.headers.get('Content-Disposition');
            const filenameMatch = contentDispositionHeader && contentDispositionHeader.match(/filename="?([^"]+)"?/);
            console.log(filenameMatch);
            const filename = filenameMatch && filenameMatch[1];
            console.log(filename);
            link.setAttribute('download', filename);
            document.body.appendChild(link);
            link.click();
            toast.success('Файл успешно скачан');
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Не удалось скачать файл");
        }
    };

    const handleDeleteAnswer = async (answerId) => {
        try {
            const response = await fetch(`http://localhost:8080/answers/delete-answer`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({answerId})
            });
            if (!response.ok) {
                throw new Error("Не удалось удалить ответ");
            }
            setAnswers(prevAnswers => prevAnswers.filter(answer => answer.answerId !== answerId));
            toast.success('Ответ успешно удален');
        } catch (error) {
            console.error(error);
            toast.error(error.message || "Не удалось удалить ответ");
        }
    };

    return (
        <div className="labwork-answer">
            {role && role === 'USER' && labWorkId && taskId &&
                <button onClick={handleAddAnswer} className="add-answer">Добавить ответ</button>}
            {isModalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close-button" onClick={handleCloseModal}>✕</span>
                        <h3>Добавить ответ</h3>
                        <input type="file" onChange={handleFileChange} className="input-file"/>
                        <textarea value={description} onChange={handleDescriptionChange}
                                  placeholder="Введите описание" className="answer-description-textarea"/>
                        <button className="submit-button" onClick={handleSubmit}>Отправить</button>
                    </div>
                </div>
            )}
            {answers.length > 0 ? (
                <div className="answer-list-container">
                    <h1 className="answer-list-title">Список ответов</h1>
                    <table className="answer-table">
                        <thead>
                        <tr>
                            <th>Описание</th>
                            {!labWorkId && <th>Лабораторная работа</th>}
                            <th>Вариант</th>
                            <th>Время загрузки</th>
                            {role && role === 'ADMIN' && <th>Пользователь</th>}
                            <th style={{width: '1%'}}>Файл</th>
                            {labWorkId && role && role === 'USER' && <th></th>}
                        </tr>
                        </thead>
                        <tbody>
                        {answers.slice().reverse().map(answer => (
                            <tr key={answer.id} className="answer-str">
                                <td>{answer.description}</td>
                                {!labWorkId && <td>{answer.labWorkName}</td>}
                                <td>{answer.variant}</td>
                                <td>{answer.dateTime}</td>
                                {role && role === 'ADMIN' && <td>{answer.username}</td>}
                                <td style={{textAlign: "center", width: '1%'}}>
                                    {answer.withFile ? (
                                        <a className="download-file-link" href="#"
                                           onClick={() => handleDownloadFile(answer.answerId)}>Скачать файл</a>
                                    ) : (
                                        "-"
                                    )}
                                </td>
                                {labWorkId && role && role === 'USER' && <td style={{textAlign: "center", width: '1%'}}>
                                    <a className="delete-answer" href="#"
                                       onClick={() => handleDeleteAnswer(answer.answerId)}>Удалить</a>
                                </td>}
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            ) : (
                <h2 className="no-answers">Не загружено ни одного ответа</h2>
            )}
        </div>
    );
};

export default AnswerList;