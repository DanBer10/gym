-- Create the 'news' table if it doesn't exist
CREATE TABLE IF NOT EXISTS news (
    id INT PRIMARY KEY,
    body VARCHAR(255) DEFAULT 'Body Text',
    title VARCHAR(255) DEFAULT 'Title Text'
);

INSERT INTO news (id)
SELECT 1
WHERE NOT EXISTS (SELECT 1 FROM news WHERE id = 1);
