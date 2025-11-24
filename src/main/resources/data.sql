INSERT INTO department (Name, is_active, date_time, description, roles)
VALUES
('HR', true, CURRENT_TIMESTAMP, 'Handles human resources', 'HR_ADMIN'),
('Finance', true, CURRENT_TIMESTAMP, 'Handles finances and accounts', 'FIN_ADMIN'),
('IT', true, CURRENT_TIMESTAMP, 'Tech and systems support', 'IT_ADMIN'),
('Marketing', false, CURRENT_TIMESTAMP, 'Marketing and branding team', 'MKT_ADMIN');
