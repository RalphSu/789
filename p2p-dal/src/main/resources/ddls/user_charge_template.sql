use yr789; 
DROP TABLE IF EXISTS user_charge_template; 
CREATE TABLE user_charge_template (
	user_charge_template_id BIGINT,
	charge_template_id BIGINT,
	user_id BIGINT
);