CREATE TABLE IF NOT EXISTS tb_roles (
  role_id BIGINT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);
MERGE INTO tb_roles (role_id, name) VALUES (1, 'admin');
MERGE INTO tb_roles (role_id, name) VALUES (2, 'attendant');