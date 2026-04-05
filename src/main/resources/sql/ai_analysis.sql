CREATE TABLE IF NOT EXISTS ai_analysis (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    web_content TEXT,
    ai_response TEXT,
    creation_date TIMESTAMP,
    update_date TIMESTAMP
);