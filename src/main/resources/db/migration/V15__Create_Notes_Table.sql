-- Create the progress table
CREATE TABLE IF NOT EXISTS notes (
    id UUID PRIMARY KEY,
    user_id INT,
    createdAt DATE,
    title VARCHAR(255) NULL,
    content TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Add an index for faster queries on user_id
CREATE INDEX IF NOT EXISTS idx_user_id ON notes(user_id);
