import React, {useEffect, useState, useRef, useContext} from "react";
import '../styles/labworkdetail.css';
import {Link} from "react-router-dom";
import {useParams} from 'react-router-dom';
import {AuthContext} from "../context";
import AnswerList from "../component/AnswerList";

const LabWorkDetail = () => {
    const {role} = useContext(AuthContext);

    const [labwork, setLabwork] = useState(null);
    const [variant, setVariant] = useState("");
    const [task, setTask] = useState(null);
    const {id} = useParams()
    const timerRef = useRef(null);
    const minVariantNumber = -2147483647;
    const maxVariantNumber = 2147483647;

    useEffect(() => {
        async function fetchLabWork() {
            try {
                const response = await fetch(`http://localhost:8080/lab-works/${id}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch labwork');
                }
                const data = await response.json();
                setLabwork(data);
            } catch (error) {
                console.error('Error fetching labwork:', error);
            }
        }

        fetchLabWork();
    }, [id]);

    const handleVariantChange = (event) => {
        const value = event.target.value;
        setVariant(value);
        if (timerRef.current) {
            clearTimeout(timerRef.current);
        }
        if (value.trim() !== "") {
            timerRef.current = setTimeout(() => {
                handleSubmit(value);
            }, 1000);
        } else {
            setTask(null)
        }
    };

    const handleSubmit = async (value) => {
        try {
            const response = await fetch(`http://localhost:8080/lab-work-tasks/${id}/${value}`);
            if (!response.ok) {
                throw new Error('Failed to fetch task');
            }
            const data = await response.json();
            setTask(data);
        } catch (error) {
            console.error('Error fetching task:', error);
        }
    };

    return (
        <div className="labworkdetail">
            {!labwork ? (
                <div>Loading...</div>
            ) : (
                <>
                    <div className="labwork-header">
                        <h1 className="labwork-name">{labwork.name}</h1>
                        {role === 'ADMIN' &&
                            <Link to={{pathname: `/labworks/${id}/edit`, state: {id}}}>
                                <div id="edit-labwork" className="expand-menu-link">Редактировать</div>
                            </Link>}
                    </div>
                    <p>{labwork.description}</p>
                    <ul>
                        {labwork.topics.map((topic, index) => (
                            <li key={index}>{topic.name}</li>
                        ))}
                    </ul>
                    <div className="input-variant-div">
                        <label id="variantInputLabel" htmlFor="variantInput">Вариант</label>
                        <input
                            type="number"
                            id="variantInput"
                            value={variant}
                            onChange={handleVariantChange}
                            min={minVariantNumber}
                            max={maxVariantNumber}
                            className="input-variant"
                        />
                    </div>
                    {task && task.tasks && task.tasks.length > 0 && <h2>Задание для варианта {task.variant}:</h2>}
                    <ul>
                        {task && task.tasks && task.tasks.map((taskItem, index) => (
                            <li key={index}>{taskItem.description}</li>
                        ))}
                    </ul>
                    {role && !task && <AnswerList labWorkId={id}/>}
                    {role && task && <AnswerList taskId={task.id} variant={task.variant} labWorkId={id}/>}
                </>
            )}
        </div>
    );
};

export default LabWorkDetail;