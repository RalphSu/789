use yr789; 
DROP TABLE IF EXISTS email_template; 
CREATE TABLE email_template (
	id BIGINT,
	subject VARCHAR(400),
	content TEXT,
	raw_update_time DATETIME,
	raw_add_time DATETIME
);