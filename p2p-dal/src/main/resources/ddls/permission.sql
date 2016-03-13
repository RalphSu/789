use yr789; 
DROP TABLE IF EXISTS permission; 
CREATE TABLE permission (
	permission_id INT,
	permission_code VARCHAR(400),
	permission_name VARCHAR(400),
	permission_operate VARCHAR(400),
	permission_callback VARCHAR(400),
	permission_note VARCHAR(400)
);