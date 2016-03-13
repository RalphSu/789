use yr789; 
DROP TABLE IF EXISTS gold_experience; 
CREATE TABLE gold_experience (
	id BIGINT,
	activity_type VARCHAR(400),
	name VARCHAR(400),
	amount DECIMAL,
	purpose VARCHAR(400),
	quantity int,
	surplus_quantity int,
	status int,
	start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	end_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);