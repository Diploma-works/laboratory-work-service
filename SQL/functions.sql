create or replace function get_random_tasks_by_topics(topic_ids integer[])
    returns TABLE(id integer, description text, topic_id integer)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        WITH RankedTasks AS (
            SELECT t.id AS task_id, t.description, t.topic_id,
                   ROW_NUMBER() OVER (PARTITION BY t.topic_id ORDER BY RANDOM()) AS rn
            FROM task t
            WHERE t.topic_id = ANY(topic_ids)
        )
        SELECT RankedTasks.task_id as id, RankedTasks.description, RankedTasks.topic_id
        FROM RankedTasks
        WHERE rn = 1;
END;
$$;