CREATE TABLE IF NOT EXISTS Ai_format.category (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    creator_id INT UNSIGNED NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO Ai_format.category (name, description, creator_id) VALUES
('生活', '与日常生活相关的分类', 1),
('媒体', '与文章创作和创新相关的分类', 1),
('娱乐', '与娱乐相关的分类', 1);