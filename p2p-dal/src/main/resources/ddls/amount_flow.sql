use yr789; 
DROP TABLE IF EXISTS amount_flow; 
CREATE TABLE amount_flow (
	amount_flow_id BIGINT,
	flow_code VARCHAR(400),
	out_user_id BIGINT,
	in_user_id BIGINT,
	amount_out VARCHAR(400),
	amount_in VARCHAR(400),
	amount BIGINT,
	status INT,
	amount_flow_type VARCHAR(400),
	note VARCHAR(400),
	date DATETIME
);