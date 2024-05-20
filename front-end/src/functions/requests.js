export async function getTopics(token) {
    try {
        console.log(token)
        const response = await fetch("http://localhost:8080/topics", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error("Failed to fetch topics");
        }
        return await response.json();
    } catch (error) {
        console.error("Error fetching topics:", error);
    }
}