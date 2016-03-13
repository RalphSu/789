use yr789; 
DROP TABLE IF EXISTS loan_auth_record; 
CREATE TABLE loan_auth_record (
	tb_base_id VARCHAR(400),
	auth_user_id BIGINT,
	loan_demand_id BIGINT,
	auth_type VARCHAR(400),
	auth_time DATETIME,
	note VARCHAR(400)
);