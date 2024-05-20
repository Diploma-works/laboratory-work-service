import {useEffect, useState} from "react";
import '../styles/labworklist.css';
import {Link} from "react-router-dom";
import LabWorkInfo from "../component/LabWorkInfo";

const LabWorkList = () => {
    const [labworks, setLabworks] = useState(null);

    useEffect(() => {
        async function fetchLabworks() {
            try {
                const response = await fetch(`http://localhost:8080/lab-works`);
                if (!response.ok) {
                    throw new Error('Failed to fetch labworks');
                }
                const data = await response.json();
                setLabworks(data);
            } catch (error) {
                console.error('Error fetching labworks:', error);
            }
        }

        fetchLabworks();

    }, []);


    return (
        <div className="labwork-list">
            {!labworks ? (
                <div>No labworks available</div>
            ) : (
                <div className="labwork-container">
                    <h1>Список лабораторных работ</h1>
                    {labworks.map((labwork) => (
                        <div key={labwork.id} className="labwork-item">
                            <Link to={`/labworks/${labwork.id}`}>
                                <LabWorkInfo labwork={labwork}/>
                            </Link>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default LabWorkList;